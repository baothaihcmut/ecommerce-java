package com.ecommerceapp.libs.events.orders;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private String id;

    private String userId;

    public static enum OrderStatus {
        PENDING,
        CONFIRMED,
        SHIPPED,
        DELIVERED,
        CANCELLED,
    }

    private OrderStatus status;

    public static enum PaymentMethod {

    }

    private PaymentMethod paymentMethod;

    private String recieveAddressId;

    private Instant createdAt;

    private Instant confirmedAt;
    private Instant shippedAt;
    private Instant recievedAt;

    private int totalAmount;

    private String shippingProviderId;

    private int shippingCost;

    private int quantity;

    private List<OrderLineEvent> orderLines;
}
