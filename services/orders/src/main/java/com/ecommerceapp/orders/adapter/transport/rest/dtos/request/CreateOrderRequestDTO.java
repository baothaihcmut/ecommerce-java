package com.ecommerceapp.orders.adapter.transport.rest.dtos.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateOrderRequestDTO(
        @NotEmpty(message = "order item must be at least 1") List<@Valid OrderItem> orderItems) {
    public record OrderItem(
            @NotEmpty(message = "product item id is required") String productItemId,
            @NotNull(message = "quantity is requred") @Min(0) Integer quantity) {
    }
}
