package com.ecommerceapp.payment.core.port.inbound.commands;

import com.ecommerceapp.payment.core.domain.enums.PaymentStatus;

public record ConfirmPaymentCommand(
        Integer amount,
        PaymentStatus status,
        String paymentId) {

}
