package com.ecommerceapp.orders.core.port.outbound.publishers;

import com.ecommerceapp.orders.core.domain.events.BulkOrderCanceledEvent;
import com.ecommerceapp.orders.core.domain.events.OrderCancelEvent;
import com.ecommerceapp.orders.core.domain.events.OrderCreatedEvent;
import com.ecommerceapp.orders.core.domain.events.OrderPaidEvent;

public interface OrderEventPublisher {
    void publishOrderCreatedEvent(OrderCreatedEvent event);

    void publishOrderPaidEvent(OrderPaidEvent event);

    void publishBulkCanceledEvent(BulkOrderCanceledEvent event);

    void publishOrderCanceledEvent(OrderCancelEvent event);
}
