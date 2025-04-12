package com.ecommerceapp.orders.adapter.transport.queue.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.ecommerceapp.libs.events.payment.PaymentFailureEvent;
import com.ecommerceapp.libs.events.payment.PaymentSuccessEvent;
import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.orders.core.port.inbound.handlers.OrderPaymentHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMqOrderEventHandler {
    private static final String PAYMENT_EXCHANGE = "payment.events";
    private static final String PAYMENT_SUCCESS_KEY = "payment.success";
    private static final String PAYMENT_FAILURE_KEY = "payment.failure";
    private static final String PAYMENT_SUCCESS_QUEUE = "orders.payment-success";
    private static final String PAYMENT_FAILURE_QUEUE = "orders.payment-failure";
    private final OrderPaymentHandler orderPaymentHandler;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = PAYMENT_SUCCESS_QUEUE, durable = "true"), exchange = @Exchange(value = PAYMENT_EXCHANGE, ignoreDeclarationExceptions = "true"), key = PAYMENT_SUCCESS_KEY))
    public void handlePaymentSuccess(PaymentSuccessEvent event) {
        try {
            orderPaymentHandler.handlePaymentSuccess(event);
        } catch (AppException e) {
            log.warn(e.getMessage(), e.getCause());
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
        }
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = PAYMENT_FAILURE_QUEUE, durable = "true"), exchange = @Exchange(value = PAYMENT_EXCHANGE, ignoreDeclarationExceptions = "true"), key = PAYMENT_FAILURE_KEY))
    public void handlePaymentFailure(PaymentFailureEvent event) {
        try {
            orderPaymentHandler.handlePaymentFailure(event);
        } catch (AppException e) {
            log.warn(e.getMessage(), e.getCause());
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
        }
    }

}
