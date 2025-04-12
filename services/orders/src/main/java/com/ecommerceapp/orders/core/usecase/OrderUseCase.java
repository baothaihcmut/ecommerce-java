package com.ecommerceapp.orders.core.usecase;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.libs.security.SecurityUtil;
import com.ecommerceapp.libs.security.SecurityUtil.UserContext;
import com.ecommerceapp.orders.core.domain.entities.Address;
import com.ecommerceapp.orders.core.domain.entities.Order;
import com.ecommerceapp.orders.core.domain.entities.ProductItem;
import com.ecommerceapp.orders.core.domain.entities.Order.CreateOrderLineArg;
import com.ecommerceapp.orders.core.domain.events.BulkOrderCanceledEvent;
import com.ecommerceapp.orders.core.domain.events.OrderCreatedEvent;
import com.ecommerceapp.orders.core.exception.ErrorCode;
import com.ecommerceapp.orders.core.port.inbound.commands.CreateOrderCommand;
import com.ecommerceapp.orders.core.port.inbound.commands.MakeOrderPaymentCommand;
import com.ecommerceapp.orders.core.port.inbound.commands.UpdateOrderAddressCommand;
import com.ecommerceapp.orders.core.port.inbound.handlers.OrderHandler;
import com.ecommerceapp.orders.core.port.inbound.results.CreateOrderResult;
import com.ecommerceapp.orders.core.port.inbound.results.MakeOrderPaymentResult;
import com.ecommerceapp.orders.core.port.inbound.results.OrderResult;
import com.ecommerceapp.orders.core.port.inbound.results.UpdateOrderAddressResult;
import com.ecommerceapp.orders.core.port.outbound.clients.PaymentClient;
import com.ecommerceapp.orders.core.port.outbound.clients.ProductItemClient;
import com.ecommerceapp.orders.core.port.outbound.clients.ShipmentClient;
import com.ecommerceapp.orders.core.port.outbound.clients.ShipmentClient.GetShippingFeeArg;
import com.ecommerceapp.orders.core.port.outbound.clients.UserAddressClient;
import com.ecommerceapp.orders.core.port.outbound.clients.PaymentClient.MakePaymentArg;
import com.ecommerceapp.orders.core.port.outbound.publishers.OrderEventPublisher;
import com.ecommerceapp.orders.core.port.outbound.repositories.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderUseCase implements OrderHandler {
    private final OrderRepository orderRepository;
    private final ProductItemClient productItemClient;
    private final OrderEventPublisher orderEventPublisher;
    private final UserAddressClient userAddressClient;
    private final ShipmentClient shipmentClient;
    private final PaymentClient paymentClient;

    @Override
    @Transactional
    public CreateOrderResult createOrder(CreateOrderCommand command) {
        UserContext userContext = SecurityUtil.getUserContext();
        // find product by id list
        List<ProductItem> productItems = productItemClient.findProductItemByIdList(
                command.orderItems().stream().map(item -> item.productItemId()).toList());
        Map<String, Integer> mapQuantity = new HashMap<>();
        command.orderItems().forEach((item) -> {
            mapQuantity.put(item.productItemId(), item.quantity());
        });
        // map product to quantity
        List<CreateOrderLineArg> productItemWithQuantity = productItems
                .stream()
                .map(item -> new CreateOrderLineArg(item, mapQuantity.get(item.getId())))
                .toList();
        Order order = new Order(
                userContext.userId(),
                productItemWithQuantity);
        // save to db
        orderRepository.save(order);
        // publish event
        orderEventPublisher.publishOrderCreatedEvent(new OrderCreatedEvent(order));
        return new CreateOrderResult(OrderResult.toOrderResult(order));
    }

    @Override
    @Transactional
    public void cancelOrderOverdue() {
        List<Order> orders = orderRepository
                .findOrderWithOrderLinesByCreatedAtBeforeTime(Instant.now().minus(30, ChronoUnit.MINUTES));
        for (Order order : orders) {
            order.canceledOrder();
        }
        if (orders.size() > 0) {
            // cancel order
            orderRepository.bulkCancelOrder(orders);
            // publish event
            orderEventPublisher.publishBulkCanceledEvent(new BulkOrderCanceledEvent(orders));
        }
    }

    @Override
    public UpdateOrderAddressResult updateOrderAddress(UpdateOrderAddressCommand command) {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            UserContext userContext = SecurityUtil.getUserContext();
            Future<Address> addressThread = executorService.submit(() -> {
                Address address = userAddressClient.findAddressById(command.addressId())
                        .orElseThrow(() -> new AppException(ErrorCode.INVALID_ORDER_RECEIVEADDRESS));
                // check address is belong to user
                if (!userContext.userId().equals(address.getUserId())) {
                    throw new AppException(ErrorCode.INVALID_ORDER_RECEIVEADDRESS);
                }
                return address;
            });
            Future<Order> orderThread = executorService.submit(() -> {
                Order order = orderRepository.findOrderById(UUID.fromString(command.orderId()))
                        .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXIT));
                if (!userContext.userId().equals(order.getUserId())) {
                    throw new AppException(ErrorCode.ORDER_IS_NOT_BELONG_TO_USER);
                }
                return order;
            });
            Order order = orderThread.get();
            Address address = addressThread.get();
            // get shipping fee
            Integer shippingFee = shipmentClient.getShippingFee(
                    new GetShippingFeeArg(
                            address,
                            command.shipProvider(),
                            order.getOrderLines().stream().map(item -> item.getProductItemId()).toList()));
            // update address ship and provider
            order.updateRecieveAddress(address, shippingFee, command.shipProvider());
            orderRepository.save(order);
            return new UpdateOrderAddressResult(OrderResult.toOrderResult(order));
        } catch (ExecutionException exception) {
            if (exception.getCause() instanceof AppException appException) {
                throw appException;
            } else {
                throw new RuntimeException(exception.getMessage(), exception.getCause());
            }
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception.getMessage(), exception.getCause());
        }

    }

    @Override
    public MakeOrderPaymentResult makeOrderPayment(MakeOrderPaymentCommand command) {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            UserContext userContext = SecurityUtil.getUserContext();
            Order order = orderRepository.findOrderById(UUID.fromString(command.id()))
                    .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXIT));
            if (!userContext.userId().equals(order.getUserId())) {
                throw new AppException(ErrorCode.ORDER_IS_NOT_BELONG_TO_USER);
            }
            order.processPayment(command.paymentMethod(), command.paymentProvider());
            Future<Void> saveDBThread = executorService.submit(() -> {
                orderRepository.save(order);
                return null;
            });
            Future<String> paymentClientThread = executorService.submit(() -> {
                return paymentClient.makePayment(
                        new MakePaymentArg(
                                order.getId().toString(),
                                command.ipAddr(),
                                order.getTotalAmountWithShippingFee(),
                                order.getPaymentProvider()));
            });
            saveDBThread.get();
            return new MakeOrderPaymentResult(
                    OrderResult.toOrderResult(order),
                    paymentClientThread.get());
        } catch (ExecutionException exception) {
            if (exception.getCause() instanceof AppException appException) {
                throw appException;
            } else {
                throw new RuntimeException(exception.getMessage(), exception.getCause());
            }
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception.getMessage(), exception.getCause());
        }

    }

}
