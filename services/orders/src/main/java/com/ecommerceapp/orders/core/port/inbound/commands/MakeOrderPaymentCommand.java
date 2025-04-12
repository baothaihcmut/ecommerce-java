package com.ecommerceapp.orders.core.port.inbound.commands;

import com.ecommerceapp.orders.core.domain.enums.PaymentMethod;
import com.ecommerceapp.orders.core.domain.enums.PaymentProvider;

public record MakeOrderPaymentCommand(
        String id,
        String ipAddr,
        PaymentMethod paymentMethod,
        PaymentProvider paymentProvider) {

}
