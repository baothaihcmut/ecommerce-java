package com.ecommerceapp.shops.core.domain.entities;

import java.time.Instant;
import java.util.List;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private String id;

    private String name;

    private String description;

    private List<String> images;

    private String thumbnail;

    private List<String> categoryIds;

    private ObjectId shopId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Variation {
        private String id;
        private String name;

    }

    private List<Variation> variations;

    private int soldTotal;

    private Instant createdAt;

    private Instant updatedAt;

}
