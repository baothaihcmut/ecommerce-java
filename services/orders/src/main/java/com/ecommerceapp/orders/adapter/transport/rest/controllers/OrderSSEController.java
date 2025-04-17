package com.ecommerceapp.orders.adapter.transport.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ecommerceapp.orders.adapter.transport.sse.OrderStatusSSEManager;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders/sse")
@RequiredArgsConstructor
public class OrderSSEController {
    private final OrderStatusSSEManager orderStatusSSEManager;

    @GetMapping(value = "/status", headers = "Accept=*/*", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter handleGetOrderStatusSSE(@RequestParam(value = "session_id", required = true) String sessionId) {
        SseEmitter emitter = orderStatusSSEManager.connect(sessionId);
        return emitter;
    }
}
