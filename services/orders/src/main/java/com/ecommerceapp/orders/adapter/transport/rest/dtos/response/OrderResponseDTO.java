package com.ecommerceapp.orders.adapter.transport.rest.dtos.response;

import java.time.Instant;
import java.util.List;

import com.ecommerceapp.orders.core.domain.enums.OrderStatus;
import com.ecommerceapp.orders.core.domain.enums.PaymentMethod;

public record OrderResponseDTO(
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
        List<OrderLineResponseDTO> orderLines) {
}
