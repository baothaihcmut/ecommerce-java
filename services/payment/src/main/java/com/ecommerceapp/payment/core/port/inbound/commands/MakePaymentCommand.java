package com.ecommerceapp.payment.core.port.inbound.commands;

import com.ecommerceapp.payment.core.domain.enums.PaymentProvider;

public record MakePaymentCommand(
                String orderId,
                Integer amount,
                String ipAddr,
                String orderInfo,
                String orderType,
                PaymentProvider paymentProvider) {
}
