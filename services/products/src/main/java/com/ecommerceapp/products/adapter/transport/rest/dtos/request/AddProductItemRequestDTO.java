package com.ecommerceapp.products.adapter.transport.rest.dtos.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AddProductItemRequestDTO(
                @NotNull(message = "quantity is required") @Min(value = 0, message = "quantity must greater than 0") Integer quantity,
                @NotNull(message = "price is required") @Min(value = 0, message = "price must greater than 0") Integer price,
                @NotNull(message = "weight is required") @Min(value = 0, message = "weigth must greater than 0") Integer weight,
                @NotNull(message = "variation list is required") List<@Valid ProductItemVariationValue> variationValues,
                @NotNull(message = "num of image is required") @Min(value = 0, message = "num of images must greater or equal 0") Integer numOfImages) {
        public record ProductItemVariationValue(
                        @NotEmpty(message = "variation id is required") String variationId,
                        @NotEmpty(message = "value is required") String value) {
        }

}
