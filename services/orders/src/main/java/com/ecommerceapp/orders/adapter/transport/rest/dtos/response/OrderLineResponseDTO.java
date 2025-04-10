package com.ecommerceapp.orders.adapter.transport.rest.dtos.response;

public record OrderLineResponseDTO(
        String id,
        String orderId,
        String productItemId,
        int quantity,
        int subTotal) {

}
