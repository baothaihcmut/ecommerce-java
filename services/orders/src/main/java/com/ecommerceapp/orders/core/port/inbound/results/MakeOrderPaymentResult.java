package com.ecommerceapp.orders.core.port.inbound.results;

public record MakeOrderPaymentResult(
        OrderResult order,
        String paymentUrl) {

}
