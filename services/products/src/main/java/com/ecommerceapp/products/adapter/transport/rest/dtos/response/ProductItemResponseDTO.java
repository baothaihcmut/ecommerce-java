package com.ecommerceapp.products.adapter.transport.rest.dtos.response;

import java.time.Instant;
import java.util.List;

public record ProductItemResponseDTO(
                String id,
                String productId,
                int quantity,
                int price,
                int weight,
                List<String> images,
                List<VariationValueResult> variationValues,
                Instant createdAt,
                Instant updatedAt) {
        public record VariationValueResult(
                        String variationId,
                        String value) {
        }

}
