package com.ecommerceapp.shops.core.port.outbound.clients;

import java.util.List;

import com.ecommerceapp.shops.core.domain.entities.Product;
import com.ecommerceapp.shops.core.port.inbound.commands.CreateProductCommand;

public interface ProductClient {
    Product createProduct(CreateProductCommand command);

    List<Product> getProductsOfShop(String shopId);
}
