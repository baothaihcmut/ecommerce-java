package com.ecommerceapp.orders.adapter.transport.rest.dtos.response;

public record MakeOrderPaymentResponseDTO(
        OrderResponseDTO order,
        String paymentUrl) {

}
