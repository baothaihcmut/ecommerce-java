package com.ecommerceapp.products.core.port.inbound.results;

import java.time.Instant;
import java.util.List;

import org.bson.types.ObjectId;

import com.ecommerceapp.products.core.domain.entities.Product;

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
    private ObjectId id;

    private String name;
    private String description;
    private List<String> images;
    private String thumbnail;
    private List<ObjectId> categoryIds;
    private String shopId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class VariationResult {
        private ObjectId id;
        private String name;
    }

    private List<VariationResult> variations;
    private int soldTotal;
    private Instant createdAt;
    private Instant updatedAt;

    public static ProductResult toProductResult(Product product) {
        return ProductResult.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .shopId(product.getShopId())
                .images(product.getImages())
                .thumbnail(product.getThumbnail())
                .categoryIds(product.getCategoryIds())
                .variations(
                        product.getVariations()
                                .stream()
                                .map(
                                        variation -> ProductResult.VariationResult
                                                .builder()
                                                .id(variation.getId())
                                                .name(variation.getName())
                                                .build())
                                .toList())
                .soldTotal(product.getSoldTotal())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();

    }

}
