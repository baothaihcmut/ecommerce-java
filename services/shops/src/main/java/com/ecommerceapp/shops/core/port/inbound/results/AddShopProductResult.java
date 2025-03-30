package com.ecommerceapp.shops.core.port.inbound.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddShopProductResult {
    private ShopResult shop;
}
