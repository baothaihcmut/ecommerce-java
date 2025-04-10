package com.ecommerceapp.products.core.port.inbound.results;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DecreaseProductItemStockResult {
    private List<ProductItemResult> productItem;
}
