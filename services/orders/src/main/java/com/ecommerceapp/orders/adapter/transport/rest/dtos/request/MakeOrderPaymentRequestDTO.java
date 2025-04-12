package com.ecommerceapp.orders.adapter.transport.rest.dtos.request;

import com.ecommerceapp.libs.events.orders.OrderEvent.PaymentMethod;
import com.ecommerceapp.orders.core.domain.enums.PaymentProvider;

import jakarta.validation.constraints.NotNull;

public record MakeOrderPaymentRequestDTO(
        @NotNull(message = "payment method is required") PaymentMethod paymentMethod,
        PaymentProvider paymentProvider) {

}
