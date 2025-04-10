package com.ecommerceapp.orders.core.port.inbound.results;

import com.ecommerceapp.orders.core.domain.entities.OrderLine;

public record OrderLineResult(
        String id,
        String orderId,
        String productItemId,
        int quantity,
        int subTotal) {
    public static OrderLineResult toOrderLineResult(OrderLine orderLine, String orderId) {
        return new OrderLineResult(
                orderLine.getId().toString(),
                orderId,
                orderLine.getProductItemId(),
                orderLine.getQuantity(),
                orderLine.getSubTotal());
    }
}
