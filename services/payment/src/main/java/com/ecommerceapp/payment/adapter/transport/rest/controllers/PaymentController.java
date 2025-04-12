package com.ecommerceapp.payment.adapter.transport.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.payment.core.domain.enums.PaymentStatus;
import com.ecommerceapp.payment.core.port.inbound.commands.ConfirmPaymentCommand;
import com.ecommerceapp.payment.core.port.inbound.handlers.PaymentHandler;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentHandler paymentHandler;

    @GetMapping("/payment-result")
    public void handlePaymentResult(
            @RequestParam("vnp_TransactionStatus") String paymentStatus,
            @RequestParam("vnp_TxnRef") String paymentId,
            @RequestParam("vnp_Amount") Integer amount) {
        paymentHandler.handlePaymentResult(new ConfirmPaymentCommand(
                amount,
                paymentStatus.equals("00") ? PaymentStatus.SUCCESS : PaymentStatus.FAILURE,
                paymentId));
    }
}
