package com.ecommerceapp.orders.core.port.inbound.results;

import java.time.Instant;
import java.util.List;

import com.ecommerceapp.orders.core.domain.entities.Order;
import com.ecommerceapp.orders.core.domain.enums.OrderStatus;
import com.ecommerceapp.orders.core.domain.enums.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResult {
    private String id;

    private String userId;

    private OrderStatus status;

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

    private List<OrderLineResult> orderLines;

    public static OrderResult toOrderResult(Order order) {
        return OrderResult.builder()
                .id(order.getId().toString())
                .userId(order.getUserId())
                .status(order.getStatus())
                .paymentMethod(order.getPaymentMethod())
                .recieveAddressId(order.getRecieveAddressId())
                .createdAt(order.getCreatedAt())
                .confirmedAt(order.getConfirmedAt())
                .shippedAt(order.getShippedAt())
                .recievedAt(order.getRecievedAt())
                .totalAmount(order.getTotalAmount())
                .shippingProviderId(order.getShippingProviderId())
                .shippingCost(order.getShippingCost())
                .quantity(order.getQuantity())
                .orderLines(order.getOrderLines().stream()
                        .map(orderLine -> OrderLineResult.toOrderLineResult(orderLine, order.getId().toString()))
                        .toList())
                .build();
    }

}
