package com.ecommerceapp.products.core.port.inbound.results;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductResult {
    ProductResult product;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UploadImageInfo {
        private String url;
        private Instant expireAt;
        private String method;
        private String contentType;
    }

    private UploadImageInfo uploadThumbnailInfo;

    private List<UploadImageInfo> uploadImageInfo;
}
