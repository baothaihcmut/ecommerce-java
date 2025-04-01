package com.ecommerceapp.shops.adapter.transport.rest.dtos.response;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProductResponseDTO {
    private String id;

    private String name;
    private String description;
    private List<String> images;
    private String thumbnail;
    private List<String> categoryIds;
    private String shopId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class VariationResponseDTO {
        private String id;
        private String name;
    }

    private List<VariationResponseDTO> variations;
    private int soldTotal;
    private Instant createdAt;
    private Instant updatedAt;

}
