package com.ecommerceapp.products.core.domain.projection;

import java.time.Instant;
import java.util.List;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductProjection {
    private ObjectId id;

    private String name;

    private String description;

    private List<String> images;

    private String thumbnail;

    private List<ObjectId> categoryIds;

    private String shopId;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Variation {
        private ObjectId id;

        private String name;

    }

    private List<Variation> variations;

    private int soldTotal;

    private Instant createdAt;

    private Instant updatedAt;
}
