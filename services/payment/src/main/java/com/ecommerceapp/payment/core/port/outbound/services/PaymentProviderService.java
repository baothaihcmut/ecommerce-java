package com.ecommerceapp.payment.core.port.outbound.services;

import com.ecommerceapp.payment.core.domain.enums.PaymentProvider;
import com.ecommerceapp.payment.core.models.PaymentInfo;

public interface PaymentProviderService {
    String generatePaymentUrl(PaymentInfo paymentInfo);

    PaymentProvider getPaymentProvider();
}
