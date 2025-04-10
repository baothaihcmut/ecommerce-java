package com.ecommerceapp.products.core.port.inbound.results;

import java.time.Instant;
import java.util.List;

import org.bson.types.ObjectId;

import com.ecommerceapp.products.core.domain.entities.Product;
import com.ecommerceapp.products.core.domain.projection.ProductProjection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Builder
public class ProductResult {
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
    public static class VariationResult {
        private String id;
        private String name;
    }

    private List<VariationResult> variations;
    private int soldTotal;
    private Instant createdAt;
    private Instant updatedAt;

    public static ProductResult toProductResult(Product product) {
        return ProductResult.builder()
                .id(product.getId().toHexString())
                .name(product.getName())
                .description(product.getDescription())
                .shopId(product.getShopId())
                .images(product.getImages())
                .thumbnail(product.getThumbnail())
                .categoryIds(product.getCategoryIds().stream().map(id -> id.toHexString()).toList())
                .variations(
                        product.getVariations()
                                .stream()
                                .map(
                                        variation -> ProductResult.VariationResult
                                                .builder()
                                                .id(variation.getId().toHexString())
                                                .name(variation.getName())
                                                .build())
                                .toList())
                .soldTotal(product.getSoldTotal())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();

    }

    public static ProductResult toProductResult(ProductProjection productProjection) {
        return ProductResult.builder()
                .id(productProjection.getId().toHexString())
                .name(productProjection.getName())
                .description(productProjection.getDescription())
                .images(productProjection.getImages())
                .thumbnail(productProjection.getThumbnail())
                .categoryIds(
                        productProjection.getCategoryIds()
                                .stream()
                                .map(ObjectId::toHexString)
                                .toList())
                .shopId(productProjection.getShopId())
                .variations(
                        productProjection.getVariations()
                                .stream()
                                .map(variation -> VariationResult.builder()
                                        .id(variation.getId().toHexString())
                                        .name(variation.getName())
                                        .build())
                                .toList())
                .soldTotal(productProjection.getSoldTotal())
                .createdAt(productProjection.getCreatedAt())
                .updatedAt(productProjection.getUpdatedAt())
                .build();
    }

}
