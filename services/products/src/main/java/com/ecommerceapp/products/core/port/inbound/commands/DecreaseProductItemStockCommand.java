package com.ecommerceapp.products.core.port.inbound.commands;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DecreaseProductItemStockCommand {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductItemStockInfo {
        private String productId;
        private Integer quantity;
    }

    List<ProductItemStockInfo> productItemStocks;
}
