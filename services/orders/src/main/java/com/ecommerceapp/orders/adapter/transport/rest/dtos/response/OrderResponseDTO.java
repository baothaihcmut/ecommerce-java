package com.ecommerceapp.orders.adapter.transport.rest.dtos.response;

import java.time.Instant;
import java.util.List;

import com.ecommerceapp.orders.core.domain.enums.OrderStatus;
import com.ecommerceapp.orders.core.domain.enums.PaymentMethod;
import com.ecommerceapp.orders.core.domain.enums.ShipProvider;

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
                ShipProvider shipProvider,
                int shippingCost,
                int quantity,
                List<OrderLineResponseDTO> orderLines) {
}
