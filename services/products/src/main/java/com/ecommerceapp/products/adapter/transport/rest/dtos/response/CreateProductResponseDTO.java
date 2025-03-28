package com.ecommerceapp.products.adapter.transport.rest.dtos.response;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductResponseDTO {
    private ProductResponseDTO product;

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
