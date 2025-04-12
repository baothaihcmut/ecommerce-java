package com.ecommerceapp.payment.core.domain.events;

import com.ecommerceapp.payment.core.domain.entities.PaymentHistory;

public record PaymentResultEvent(
        PaymentHistory paymentHistory) {

}
