package com.ecommerceapp.orders.core.usecase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.security.SecurityUtil;
import com.ecommerceapp.libs.security.SecurityUtil.UserContext;
import com.ecommerceapp.orders.core.domain.entities.Order;
import com.ecommerceapp.orders.core.domain.entities.ProductItem;
import com.ecommerceapp.orders.core.domain.entities.Order.CreateOrderLineArg;
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
                command.getOrderItems().stream().map(item -> item.getProductItemId()).toList());
        Map<String, Integer> mapQuantity = new HashMap<>();
        command.getOrderItems().forEach((item) -> {
            mapQuantity.put(item.getProductItemId(), item.getQuantity());
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
        orderEventPublisher.publishOrderCreatedEvent(OrderCreatedEvent.builder().order(order).build());
        return CreateOrderResult.builder().order(OrderResult.toOrderResult(order)).build();
    }

}
