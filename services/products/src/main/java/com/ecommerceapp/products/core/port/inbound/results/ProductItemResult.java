package com.ecommerceapp.products.core.port.inbound.results;

import java.time.Instant;
import java.util.List;

import org.bson.types.ObjectId;

import com.ecommerceapp.products.core.domain.entities.ProductItem;
import com.ecommerceapp.products.core.domain.projection.ProductItemProjection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductItemResult {

        private String id;

        private String productId;

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
                                .productId(productItem.getProductId().toHexString())
                                .quantity(productItem.getQuantity())
                                .price(productItem.getPrice())
                                .images(productItem.getImages())
                                .variationValues(productItem.getVariationValues().stream()
                                                .map(val -> VariationValueResult.builder()
                                                                .variationId(val.getVariationId())
                                                                .value(val.getValue()).build())
                                                .toList())
                                .createdAt(productItem.getCreatedAt())
                                .updatedAt(productItem.getUpdatedAt())
                                .build();

        }

        public static ProductItemResult toProductItemResult(ProductItemProjection projection) {
                return ProductItemResult.builder()
                                .id(projection.getId().toHexString())
                                .productId(projection.getProductId().toHexString())
                                .quantity(projection.getQuantity())
                                .price(projection.getPrice())
                                .images(projection.getImages())
                                .variationValues(
                                                projection.getVariationValues().stream()
                                                                .map(v -> VariationValueResult.builder()
                                                                                .variationId(v.getVariationId())
                                                                                .value(v.getValue())
                                                                                .build())
                                                                .toList())
                                .createdAt(projection.getCreatedAt())
                                .updatedAt(projection.getUpdatedAt())
                                .build();
        }

}
