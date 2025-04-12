package com.ecommerceapp.payment.core.models;

public record PaymentInfo(
                Integer amount,
                String ipAdrr,
                String orderInfo,
                String orderType,
                String paymentId) {
}
