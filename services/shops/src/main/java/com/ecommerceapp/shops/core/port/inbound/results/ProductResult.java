package com.ecommerceapp.shops.core.port.inbound.results;

import java.time.Instant;
import java.util.List;

import com.ecommerceapp.shops.core.domain.entities.Product;

public record ProductResult(
        String id,
        String name,
        String description,
        List<String> images,
        String thumbnail,
        List<String> categoryIds,
        String shopId,
        List<VariationResult> variations,
        int soldTotal,
        Instant createdAt,
        Instant updatedAt) {
    public static record VariationResult(
            String id,
            String name) {
    }

    public static ProductResult toProductResult(Product product) {
        return new ProductResult(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImages(),
                product.getThumbnail(),
                product.getCategoryIds(),
                product.getShopId().toHexString(),
                product.getVariations().stream()
                        .map(variation -> new VariationResult(
                                variation.getId(),
                                variation.getName()))
                        .toList(),
                product.getSoldTotal(),
                product.getCreatedAt(),
                product.getUpdatedAt());
    }
}