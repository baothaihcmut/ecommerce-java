package com.ecommerceapp.orders.adapter.transport.queue.rabbitmq.producer;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.events.utils.EventUtil;
import com.ecommerceapp.libs.rabbitmq.RabbitMqService;
import com.ecommerceapp.orders.adapter.transport.queue.mappers.OrderEventMapper;
import com.ecommerceapp.orders.core.domain.events.BulkOrderCanceledEvent;
import com.ecommerceapp.orders.core.domain.events.OrderCancelEvent;
import com.ecommerceapp.orders.core.domain.events.OrderConfirmedEvent;
import com.ecommerceapp.orders.core.domain.events.OrderCreatedEvent;
import com.ecommerceapp.orders.core.domain.events.OrderPaidEvent;
import com.ecommerceapp.orders.core.port.outbound.publishers.OrderEventPublisher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitMqOrderEventPublisher implements OrderEventPublisher {
    private final RabbitMqService rabbitMqService;
    private final OrderEventMapper orderEventMapper;

    private static final String ORDER_EXCHANGE = "orders.events";

    private static final String ORDER_CREATED_KEY = "orders.created";

    private static final String ORDER_BULK_CANCEL_KEY = "orders.bulk-canceled";

    private static final String ORDER_PAID_KEY = "orders.paid";

    private static final String ORDER_CANCELED_KEY = "orders.canceld";

    private static final String ORDER_CONFIRMED_KEY = "orders.confirmed";

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
        // publish to event bus
        applicationEventPublisher.publishEvent(event);
        rabbitMqService.sendMessage(
                ORDER_EXCHANGE,
                ORDER_CREATED_KEY,
                orderEventMapper.toOrderCreatedEvent(event),
                EventUtil.getCommonHeader(
                        "orders-service",
                        "OrderCreated"));
    }

    @Override
    public void publishBulkCanceledEvent(BulkOrderCanceledEvent event) {
        applicationEventPublisher.publishEvent(event);
        rabbitMqService.sendMessage(
                ORDER_EXCHANGE,
                ORDER_BULK_CANCEL_KEY,
                event,
                EventUtil.getCommonHeader("orders-service", "BulkOrderCanceled"));

    }

    @Override
    public void publishOrderPaidEvent(OrderPaidEvent event) {
        applicationEventPublisher.publishEvent(event);
        rabbitMqService.sendMessage(
                ORDER_EXCHANGE,
                ORDER_PAID_KEY,
                orderEventMapper.toOrderPaidEvent(event),
                EventUtil.getCommonHeader("orders-event", "OrderPaid"));
    }

    @Override
    public void publishOrderCanceledEvent(OrderCancelEvent event) {
        applicationEventPublisher.publishEvent(event);
        rabbitMqService.sendMessage(
                ORDER_EXCHANGE,
                ORDER_CANCELED_KEY,
                orderEventMapper.toOrderCanceledEvent(event),
                EventUtil.getCommonHeader("orders-event", "OrderCanceled"));
    }

    @Override
    public void publishOrderConfirmedEvent(OrderConfirmedEvent event) {
        applicationEventPublisher.publishEvent(event);
        rabbitMqService.sendMessage(
                ORDER_EXCHANGE,
                ORDER_CONFIRMED_KEY,
                orderEventMapper.toOrderConfirmedEvent(event),
                EventUtil.getCommonHeader("orders-event", "OrderCanceled"));
    }

}
