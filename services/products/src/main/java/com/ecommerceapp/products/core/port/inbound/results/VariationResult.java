package com.ecommerceapp.products.core.port.inbound.results;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VariationResult {
    private ObjectId id;
    private String name;
}
