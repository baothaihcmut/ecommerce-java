package com.ecommerceapp.shops.core.port.inbound.handlers;

import com.ecommerceapp.shops.core.port.inbound.commands.CreateProductCommand;
import com.ecommerceapp.shops.core.port.inbound.results.CreateProductResult;

public interface ShopProductHandler {
    CreateProductResult createProduct(CreateProductCommand command);
}
