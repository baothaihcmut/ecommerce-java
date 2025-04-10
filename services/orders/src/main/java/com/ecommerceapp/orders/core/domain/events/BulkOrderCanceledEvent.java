package com.ecommerceapp.orders.core.domain.events;

import java.util.List;

import com.ecommerceapp.orders.core.domain.entities.Order;

public record BulkOrderCanceledEvent(List<Order> orders) {

}
