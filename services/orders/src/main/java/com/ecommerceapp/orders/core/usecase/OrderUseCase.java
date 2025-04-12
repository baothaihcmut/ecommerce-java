package com.ecommerceapp.orders.core.usecase;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.security.SecurityUtil;
import com.ecommerceapp.libs.security.SecurityUtil.UserContext;
import com.ecommerceapp.orders.core.domain.entities.Order;
import com.ecommerceapp.orders.core.domain.entities.ProductItem;
import com.ecommerceapp.orders.core.domain.entities.Order.CreateOrderLineArg;
import com.ecommerceapp.orders.core.domain.events.BulkOrderCanceledEvent;
import com.ecommerceapp.orders.core.domain.events.OrderCreatedEvent;
import com.ecommerceapp.orders.core.port.inbound.commands.CreateOrderCommand;
import com.ecommerceapp.orders.core.port.inbound.handlers.OrderHandler;
import com.ecommerceapp.orders.core.port.inbound.results.CreateOrderResult;
import com.ecommerceapp.orders.core.port.inbound.results.OrderResult;
import com.ecommerceapp.orders.core.port.outbound.clients.ProductItemClient;
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

}
