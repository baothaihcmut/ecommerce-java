package com.ecommerceapp.payment.core.port.inbound.handlers;

import com.ecommerceapp.payment.core.port.inbound.commands.ConfirmPaymentCommand;
import com.ecommerceapp.payment.core.port.inbound.commands.MakePaymentCommand;
import com.ecommerceapp.payment.core.port.inbound.results.MakePaymentResult;

public interface PaymentHandler {
    MakePaymentResult makePayment(MakePaymentCommand makePaymentCommand);

    void handlePaymentResult(ConfirmPaymentCommand paymentResult);
}
