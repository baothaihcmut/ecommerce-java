package com.ecommerceapp.orders.adapter.transport.queue.rabbitmq.producer;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.rabbitmq.RabbitMqService;
import com.ecommerceapp.orders.adapter.transport.queue.mappers.OrderEventMapper;
import com.ecommerceapp.orders.adapter.transport.queue.utils.QueueUtil;
import com.ecommerceapp.orders.core.domain.events.BulkOrderCanceledEvent;
import com.ecommerceapp.orders.core.domain.events.OrderCreatedEvent;
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

    @Override
    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
        rabbitMqService.sendMessage(
                ORDER_EXCHANGE,
                ORDER_CREATED_KEY,
                orderEventMapper.toOrderCreatedEvent(event),
                QueueUtil.getCommonHeader("OrderCreated"));
    }

    @Override
    public void publishBulkCanceledEvent(BulkOrderCanceledEvent event) {
        rabbitMqService.sendMessage(
                ORDER_EXCHANGE,
                ORDER_BULK_CANCEL_KEY,
                event,
                QueueUtil.getCommonHeader("BulkOrderCanceled"));

    }

}
