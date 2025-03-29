package com.ecommerceapp.products.core.port.inbound.results;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadImageResult {
    private String url;
    private Instant expireAt;
    private String method;
    private String contentType;
}
