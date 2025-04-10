package com.ecommerceapp.orders.core.port.inbound.results;

import java.time.Instant;
import java.util.List;

import com.ecommerceapp.orders.core.domain.entities.Order;
import com.ecommerceapp.orders.core.domain.enums.OrderStatus;
import com.ecommerceapp.orders.core.domain.enums.PaymentMethod;

public record OrderResult(
        String id,
        String userId,
        OrderStatus status,
        PaymentMethod paymentMethod,
        String recieveAddressId,
        Instant createdAt,
        Instant confirmedAt,
        Instant shippedAt,
        Instant recievedAt,
        int totalAmount,
        String shippingProviderId,
        int shippingCost,
        int quantity,
        List<OrderLineResult> orderLines) {

    public static OrderResult toOrderResult(Order order) {
        return new OrderResult(
                order.getId().toString(),
                order.getUserId(),
                order.getStatus(),
                order.getPaymentMethod(),
                order.getRecieveAddressId(),
                order.getCreatedAt(),
                order.getConfirmedAt(),
                order.getShippedAt(),
                order.getRecievedAt(),
                order.getTotalAmount(),
                order.getShippingProviderId(),
                order.getShippingCost(),
                order.getQuantity(),
                order.getOrderLines().stream()
                        .map(orderLine -> OrderLineResult.toOrderLineResult(orderLine, order.getId().toString()))
                        .toList());
    }
}
