package com.ecommerceapp.products.core.port.inbound.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductItemWithProductResult {
    ProductItemResult productItem;
    ProductResult product;
}
