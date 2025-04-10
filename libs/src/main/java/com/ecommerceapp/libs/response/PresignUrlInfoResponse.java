package com.ecommerceapp.libs.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PresignUrlInfoResponse {
    private String url;
    private Instant expireAt;
    private String method;
    private String contentType;
}
