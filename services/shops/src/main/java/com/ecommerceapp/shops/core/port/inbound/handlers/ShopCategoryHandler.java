package com.ecommerceapp.shops.core.port.inbound.handlers;

import com.ecommerceapp.shops.core.port.inbound.commands.CreateShopCategoryCommand;
import com.ecommerceapp.shops.core.port.inbound.queries.GetShopCategoriesOfShopQuery;
import com.ecommerceapp.shops.core.port.inbound.results.CreateShopCategoryResult;
import com.ecommerceapp.shops.core.port.inbound.results.GetShopCategoriesOfShopResult;

public interface ShopCategoryHandler {
    CreateShopCategoryResult createShopCategory(CreateShopCategoryCommand categoryCommand);

    GetShopCategoriesOfShopResult getCategoriesOfShop(GetShopCategoriesOfShopQuery query);
}
