package com.ecommerceapp.libs.events.orders;

import java.time.Instant;
import java.util.List;

public record OrderEvent(
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
        List<OrderLineEvent> orderLines) {
    public enum OrderStatus {
        PENDING,
        PROCESSPAYMENT,
        PAID,
        CONFIRMED,
        SHIPPED,
        DELIVERED,
        CANCELLED,
    }

    public enum PaymentMethod {
        COD,
        ADVANCE
    }
}
