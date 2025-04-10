package com.ecommerceapp.products.core.domain.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemWithProductProjection extends ProductItemProjection {
    private ProductProjection product;
}
