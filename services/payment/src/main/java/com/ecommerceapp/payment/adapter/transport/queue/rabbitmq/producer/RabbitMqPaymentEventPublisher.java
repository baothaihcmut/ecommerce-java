package com.ecommerceapp.payment.adapter.transport.queue.rabbitmq.producer;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.events.payment.PaymentFailureEvent;
import com.ecommerceapp.libs.events.payment.PaymentSuccessEvent;
import com.ecommerceapp.libs.grpc.utils.GrpcContextKey;
import com.ecommerceapp.libs.rabbitmq.RabbitMqService;
import com.ecommerceapp.payment.core.domain.enums.PaymentStatus;
import com.ecommerceapp.payment.core.domain.events.PaymentResultEvent;
import com.ecommerceapp.payment.core.port.outbound.publishers.PaymentEventPublisher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitMqPaymentEventPublisher implements PaymentEventPublisher {
    private final RabbitMqService rabbitMqService;
    private static final String PAYMENT_EXCHANGE = "payment.events";
    private static final String PAYMENT_SUCCESS_KEY = "payment.success";
    private static final String PAYMENT_FAILURE_KEY = "payment.failure";

    @Override
    public void publishPaymentResultEvent(PaymentResultEvent paymentResultEvent) {
        Map<String, String> headers = Map.of(
                "userId", GrpcContextKey.USER_CONTEXT.get().userId());
        if (paymentResultEvent.paymentHistory().getPaymentStatus().equals(PaymentStatus.SUCCESS)) {
            headers.put("eventType", "PaymentSuccess");
            rabbitMqService.sendMessage(
                    PAYMENT_EXCHANGE,
                    PAYMENT_SUCCESS_KEY,
                    new PaymentSuccessEvent(paymentResultEvent.paymentHistory().getOrderId()),
                    headers);
        } else {
            headers.put("eventType", "PaymentFailure");
            rabbitMqService.sendMessage(PAYMENT_EXCHANGE, PAYMENT_FAILURE_KEY,
                    new PaymentFailureEvent(paymentResultEvent.paymentHistory().getOrderId()),
                    headers);
        }
    }

}
