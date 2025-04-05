package com.ecommerceapp.shops.core.port.inbound.handlers;

import com.ecommerceapp.shops.core.port.inbound.commands.CreateProductCommand;
import com.ecommerceapp.shops.core.port.inbound.queries.GetProductsOfShopQuery;
import com.ecommerceapp.shops.core.port.inbound.results.CreateProductResult;
import com.ecommerceapp.shops.core.port.inbound.results.GetProductsOfShopResult;

public interface ShopProductHandler {
    CreateProductResult createProduct(CreateProductCommand command);

    GetProductsOfShopResult getProductsOfShop(GetProductsOfShopQuery query);
}
