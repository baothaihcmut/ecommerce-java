package com.ecommerceapp.orders.core.usecase;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.events.payment.PaymentFailureEvent;
import com.ecommerceapp.libs.events.payment.PaymentSuccessEvent;
import com.ecommerceapp.orders.core.domain.entities.Order;
import com.ecommerceapp.orders.core.domain.events.OrderCancelEvent;
import com.ecommerceapp.orders.core.domain.events.OrderPaidEvent;
import com.ecommerceapp.orders.core.port.inbound.handlers.OrderPaymentHandler;
import com.ecommerceapp.orders.core.port.outbound.publishers.OrderEventPublisher;
import com.ecommerceapp.orders.core.port.outbound.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderPaymentUseCase implements OrderPaymentHandler {
    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    @Override
    public void handlePaymentSuccess(PaymentSuccessEvent paymentSuccessEvent) {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            UUID orderId = UUID.fromString(paymentSuccessEvent.orderId());
            Order order = orderRepository.findOrderById(orderId).orElse(null);
            if (order == null) {
                log.error("order not found");
                return;
            }
            order.paymentSuccess();

            executor.submit(() -> {
                orderRepository.save(order);
            });
            executor.submit(() -> {
                orderEventPublisher.publishOrderPaidEvent(new OrderPaidEvent(order));
            });
            executor.wait();

        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

    }

    @Override
    public void handlePaymentFailure(PaymentFailureEvent paymentFailureEvent) {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            UUID orderId = UUID.fromString(paymentFailureEvent.orderId());
            Order order = orderRepository.findOrderById(orderId).orElse(null);
            if (order == null) {
                log.error("order not found");
                return;
            }
            order.canceledOrder();

            executor.submit(() -> {
                orderRepository.save(order);
            });
            executor.submit(() -> {
                orderEventPublisher.publishOrderCanceledEvent(new OrderCancelEvent(order));
            });
            executor.wait();

        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

}
