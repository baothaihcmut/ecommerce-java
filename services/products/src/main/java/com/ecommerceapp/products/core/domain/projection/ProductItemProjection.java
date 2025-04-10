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
public class ProductItemProjection {
    private ObjectId id;

    private ObjectId productId;

    private int quantity;

    private int price;

    private List<String> images;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VariationValue {
        private ObjectId variationId;
        private String value;
    }

    private List<VariationValue> variationValues;

    private Instant createdAt;

    private Instant updatedAt;
}
