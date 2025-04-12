package com.ecommerceapp.orders.core.port.outbound.clients;

import com.ecommerceapp.orders.core.domain.enums.PaymentProvider;

public interface PaymentClient {
    public record MakePaymentArg(
            String orderId,
            String ipAddr,
            Integer amount,
            PaymentProvider paymentProvider) {
    }

    String makePayment(MakePaymentArg arg);
}
