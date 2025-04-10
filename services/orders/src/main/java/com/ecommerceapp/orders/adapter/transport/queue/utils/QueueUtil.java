package com.ecommerceapp.orders.adapter.transport.queue.utils;

import java.util.Map;

public class QueueUtil {
    public static Map<String, String> getCommonHeader(String evenType) {
        return Map.of(
                "eventSource", "orders-service",
                "eventType", evenType);

    }
}
