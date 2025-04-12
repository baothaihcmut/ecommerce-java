package com.ecommerceapp.orders.core.port.inbound.handlers;

import com.ecommerceapp.libs.events.payment.PaymentFailureEvent;
import com.ecommerceapp.libs.events.payment.PaymentSuccessEvent;

public interface OrderPaymentHandler {
    void handlePaymentSuccess(PaymentSuccessEvent paymentSuccessEvent);

    void handlePaymentFailure(PaymentFailureEvent paymentFailureEvent);
}
