package com.ecommerceapp.orders.adapter.transport.rest.dtos.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateOrderRequestDTO(
        @NotNull(message = "order item must not be null") @Size(min = 1, message = "order must contain at least 1 item") List<@Valid OrderItem> orderItems) {
    public record OrderItem(
            @NotEmpty(message = "product item id is required") String productItemId,
            @NotNull(message = "quantity is requred") @Min(0) Integer quantity) {
    }
}
