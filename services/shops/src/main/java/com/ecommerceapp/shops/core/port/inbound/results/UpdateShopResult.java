package com.ecommerceapp.shops.core.port.inbound.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateShopResult {
    ShopResult shop;
}
