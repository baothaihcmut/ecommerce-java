package com.ecommerceapp.orders.core.port.inbound.commands;

import java.util.List;

public record CreateOrderCommand(List<OrderItem> orderItems) {
    public record OrderItem(String productItemId, Integer quantity) {
    }
}