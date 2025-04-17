package com.ecommerceapp.orders.adapter.transport.sse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ecommerceapp.libs.redis.RedisService;
import com.ecommerceapp.orders.core.domain.events.OrderCancelEvent;
import com.ecommerceapp.orders.core.domain.events.OrderConfirmedEvent;
import com.ecommerceapp.orders.core.domain.events.OrderCreatedEvent;
import com.ecommerceapp.orders.core.domain.events.OrderPaidEvent;
import com.ecommerceapp.orders.core.port.inbound.handlers.OrderHandler;
import com.ecommerceapp.orders.core.port.inbound.queries.GetOrderStatusQuery;
import com.ecommerceapp.orders.core.port.inbound.results.GetOrderStatusResult;
import com.ecommerceapp.orders.core.port.inbound.results.OrderResult;
import com.ecommerceapp.orders.core.usecase.OrderUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderStatusSSEManager {
    private Map<String, List<Map.Entry<String, SseEmitter>>> sseCons = new ConcurrentHashMap<>();
    private final OrderHandler orderHandler;
    private final RedisService redisService;

    public SseEmitter connect(String sessionId) {
        SseEmitter sseEmitter = new SseEmitter();
        try {
            GetOrderStatusResult result = orderHandler.getOrderStatus(new GetOrderStatusQuery(sessionId));
            OrderResult order = result.order();
            sseEmitter.send(SseEmitter.event()
                    .name("init")
                    .data(order.status().toString()));
            if (!sseCons.containsKey(order.id())) {
                sseCons.put(order.id(), new ArrayList<>());
            }
            sseCons.get(order.id()).add(Map.entry(sessionId, sseEmitter));
            sseEmitter.onCompletion(() -> sseCons.remove(order.id()));
            sseEmitter.onTimeout(() -> sseCons.remove(order.id()));
        } catch (Exception e) {
            System.out.println(e);
            sseEmitter.completeWithError(e);
        }
        return sseEmitter;
    }

    @Async
    @EventListener
    public void handleOrderEvents(Object event) {
        switch (event) {
            case OrderCreatedEvent created -> {
                handleSsePush(created.order().getId().toString(), created.order().getStatus().toString(), "created");
            }
            case OrderConfirmedEvent confirmed -> {
                handleSsePush(confirmed.order().getId().toString(), confirmed.order().getStatus().toString(),
                        "confirmed");
            }
            case OrderCancelEvent cancelled -> {
                handleSsePush(cancelled.order().getId().toString(), cancelled.order().getStatus().toString(),
                        "cancelled");
            }
            case OrderPaidEvent paid -> {
                handleSsePush(paid.order().getId().toString(), paid.order().getStatus().toString(), "paid");
            }
            default -> {

            }
        }
    }

    private void handleSsePush(String id, String data, String name) {
        List<Entry<String, SseEmitter>> sses = sseCons.get(id);
        for (Entry<String, SseEmitter> client : sses) {
            String sessionId = client.getKey();
            try {
                if (redisService.existByKey(String.format("%s:%s", OrderUseCase.orderStatusSessionKey, sessionId))) {
                    SseEmitter emitter = client.getValue();
                    try {
                        emitter.send(SseEmitter.event()
                                .name(name)
                                .data(data));
                    } catch (IOException e) {
                        log.error(e.getMessage(), e.getCause());
                        emitter.completeWithError(e);

                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e.getCause());
                throw new RuntimeException(e.getCause());
            }
        }

    }
}
