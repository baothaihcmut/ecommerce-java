package com.ecommerceapp.libs.events.utils;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class EventUtil {
    public static Map<String, String> getCommonHeader(
            String eventSource,
            String eventType) {
        return Map.of(
                "eventId", UUID.randomUUID().toString(),
                "eventSource", eventSource,
                "eventType", eventType,
                "timestamp", Instant.now().toString());
    }
}
