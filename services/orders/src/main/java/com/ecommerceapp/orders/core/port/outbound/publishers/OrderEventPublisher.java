package com.ecommerceapp.orders.core.port.outbound.publishers;

import com.ecommerceapp.orders.core.domain.events.OrderCreatedEvent;

public interface OrderEventPublisher {
    void publishOrderCreatedEvent(OrderCreatedEvent event);
}
