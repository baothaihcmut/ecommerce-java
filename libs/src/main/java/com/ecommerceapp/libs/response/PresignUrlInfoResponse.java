package com.ecommerceapp.libs.response;

import java.time.Instant;

public record PresignUrlInfoResponse(
        String url,
        Instant expireAt,
        String method,
        String contentType) {

}
