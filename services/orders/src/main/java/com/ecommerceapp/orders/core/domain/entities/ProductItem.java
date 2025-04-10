package com.ecommerceapp.orders.core.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItem {
    private String id;
    private String productId;
    private Integer quantity;
    private Integer price;
    private String shopId;
}
