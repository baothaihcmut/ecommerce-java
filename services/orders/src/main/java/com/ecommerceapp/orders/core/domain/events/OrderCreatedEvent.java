package com.ecommerceapp.orders.core.domain.events;

import com.ecommerceapp.orders.core.domain.entities.Order;

public record OrderCreatedEvent(Order order) {
}
