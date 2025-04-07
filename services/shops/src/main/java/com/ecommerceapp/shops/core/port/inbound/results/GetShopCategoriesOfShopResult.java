package com.ecommerceapp.shops.core.port.inbound.results;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GetShopCategoriesOfShopResult {
    private List<ShopCategoryResult> shopCategories;
}
