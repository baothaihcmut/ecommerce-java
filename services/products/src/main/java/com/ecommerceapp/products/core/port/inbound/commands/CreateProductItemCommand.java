package com.ecommerceapp.products.core.port.inbound.commands;

import java.util.List;

public record CreateProductItemCommand(
                String productId,
                Integer quantity,
                Integer price,
                Integer weight,
                List<ProductItemVariationValue> variationValues,
                Integer numOfImages) {
        public record ProductItemVariationValue(
                        String variationId,
                        String value) {
        }
}