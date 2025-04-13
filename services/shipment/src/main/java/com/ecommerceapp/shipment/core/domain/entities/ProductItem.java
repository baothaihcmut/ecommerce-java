package com.ecommerceapp.shipment.core.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItem {
    private String id;
    private Integer weight;
    private Integer price;
}
