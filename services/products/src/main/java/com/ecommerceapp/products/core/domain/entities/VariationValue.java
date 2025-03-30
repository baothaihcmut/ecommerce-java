package com.ecommerceapp.products.core.domain.entities;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariationValue {
    private ObjectId variationId;
    private String value;
}
