package com.ecommerceapp.payment.core.port.outbound.publishers;

import com.ecommerceapp.payment.core.domain.events.PaymentResultEvent;

public interface PaymentEventPublisher {
    void publishPaymentResultEvent(PaymentResultEvent paymentResultEvent);
}
