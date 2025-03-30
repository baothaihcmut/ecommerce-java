package com.ecommerceapp.products.core.port.inbound.results;

import java.time.Instant;
import java.util.List;

import org.bson.types.ObjectId;

import com.ecommerceapp.products.core.domain.entities.ProductItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductItemResult {

    private ObjectId id;

    private ObjectId productId;

    private int quantity;

    private int price;

    private List<String> images;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class VariationValueResult {
        private ObjectId variationId;
        private String value;

    }

    private List<VariationValueResult> variationValues;

    private Instant createdAt;

    private Instant updatedAt;

    public static ProductItemResult toProductItemResult(ProductItem productItem) {
        return ProductItemResult.builder()
                .productId(productItem.getProductId())
                .quantity(productItem.getQuantity())
                .price(productItem.getPrice())
                .images(productItem.getImages())
                .variationValues(productItem.getVariationValues().stream()
                        .map(val -> VariationValueResult.builder().variationId(val.getVariationId())
                                .value(val.getValue()).build())
                        .toList())
                .createdAt(productItem.getCreatedAt())
                .updatedAt(productItem.getUpdatedAt())
                .build();

    }
}
