package com.ecommerceapp.products.core.port.inbound.commands;

import java.util.List;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateProductItemCommand {
    private ObjectId productId;
    private Integer quantity;
    private Integer price;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductItemVariaionValue {
        private ObjectId variationId;
        private String value;
    }

    private List<ProductItemVariaionValue> variationValues;

    private Integer numOfImages;

}
