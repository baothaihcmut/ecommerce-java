package com.ecommerceapp.products.core.port.inbound.results;

import java.time.Instant;
import java.util.List;

import com.ecommerceapp.products.core.domain.entities.ProductItem;
import com.ecommerceapp.products.core.domain.projection.ProductItemProjection;

public record ProductItemResult(
                String id,
                String productId,
                int quantity,
                int price,
                List<String> images,
                List<VariationValueResult> variationValues,
                Instant createdAt,
                Instant updatedAt) {

        public static ProductItemResult fromProductItem(ProductItem productItem) {
                return new ProductItemResult(
                                productItem.getId().toHexString(),
                                productItem.getProductId().toHexString(),
                                productItem.getQuantity(),
                                productItem.getPrice(),
                                productItem.getImages(),
                                productItem.getVariationValues().stream()
                                                .map(val -> new VariationValueResult(val.getVariationId().toHexString(),
                                                                val.getValue()))
                                                .toList(),
                                productItem.getCreatedAt(),
                                productItem.getUpdatedAt());
        }

        public static ProductItemResult fromProjection(ProductItemProjection projection) {
                return new ProductItemResult(
                                projection.getId().toHexString(),
                                projection.getProductId().toHexString(),
                                projection.getQuantity(),
                                projection.getPrice(),
                                projection.getImages(),
                                projection.getVariationValues().stream()
                                                .map(v -> new VariationValueResult(v.getVariationId().toHexString(),
                                                                v.getValue()))
                                                .toList(),
                                projection.getCreatedAt(),
                                projection.getUpdatedAt());
        }

        public record VariationValueResult(
                        String variationId,
                        String value) {
        }
}