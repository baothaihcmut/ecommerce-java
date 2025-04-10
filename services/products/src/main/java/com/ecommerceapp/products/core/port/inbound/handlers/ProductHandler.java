package com.ecommerceapp.products.core.port.inbound.handlers;

import com.ecommerceapp.products.core.port.inbound.commands.CreateProductCommand;
import com.ecommerceapp.products.core.port.inbound.commands.DeleteProductCommand;
import com.ecommerceapp.products.core.port.inbound.queries.GetProductsOfShopQuery;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductResult;
import com.ecommerceapp.products.core.port.inbound.results.DeleteProductResult;
import com.ecommerceapp.products.core.port.inbound.results.GetProductsOfShopResult;

public interface ProductHandler {
    CreateProductResult createProduct(CreateProductCommand command);

    DeleteProductResult deleteProduct(DeleteProductCommand command);

    GetProductsOfShopResult getProductsOfShop(GetProductsOfShopQuery query);

}
