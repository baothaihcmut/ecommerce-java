package com.ecommerceapp.payment.core.port.inbound.results;

import com.ecommerceapp.payment.core.domain.enums.PaymentProvider;

public record MakePaymentResult(
        PaymentProvider paymentProvider,
        String paymentUrl) {
}
