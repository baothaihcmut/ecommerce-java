package com.ecommerceapp.products.adapter.transport.rest.dtos.request;

import java.util.List;

import org.bson.types.ObjectId;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductItemRequestDTO {
    @NotNull(message = "product id is required")
    private ObjectId productId;

    @NotNull(message = "quantity is required")
    @Min(value = 0, message = "quantity must greater or equal 0")
    private Integer quantity;

    @NotNull(message = "price is required")
    @Min(value = 0, message = "price must greater or equal 0")
    private Integer price;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VariationValue {
        @NotNull(message = "variation value is required")
        private ObjectId variationId;

        @NotEmpty(message = "variation value is required")
        private String value;

    }

    @Valid
    private List<VariationValue> variationValues;

    private Integer numOfImages;

}
