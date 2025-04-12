package com.ecommerceapp.orders.core.port.inbound.results;

import java.time.Instant;
import java.util.List;

import com.ecommerceapp.orders.core.domain.entities.Order;
import com.ecommerceapp.orders.core.domain.enums.OrderStatus;
import com.ecommerceapp.orders.core.domain.enums.PaymentMethod;
import com.ecommerceapp.orders.core.domain.enums.ShipProvider;

public record OrderResult(
        String id,
        String userId,
        OrderStatus status,
        PaymentMethod paymentMethod,
        String recieveAddress,
        Instant createdAt,
        Instant confirmedAt,
        Instant shippedAt,
        Instant recievedAt,
        int totalAmount,
        ShipProvider shippingProvider,
        int shippingCost,
        int quantity,
        List<OrderLineResult> orderLines) {

    public static OrderResult toOrderResult(Order order) {
        return new OrderResult(
                order.getId().toString(),
                order.getUserId(),
                order.getStatus(),
                order.getPaymentMethod(),
                order.getRecieveAddress(),
                order.getCreatedAt(),
                order.getConfirmedAt(),
                order.getShippedAt(),
                order.getRecievedAt(),
                order.getTotalAmount(),
                order.getShipProvider(),
                order.getShippingCost(),
                order.getQuantity(),
                order.getOrderLines().stream()
                        .map(orderLine -> OrderLineResult.toOrderLineResult(orderLine, order.getId().toString()))
                        .toList());
    }
}
