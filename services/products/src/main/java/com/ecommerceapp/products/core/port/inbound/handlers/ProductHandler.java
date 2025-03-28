package com.ecommerceapp.products.core.port.inbound.handlers;

import com.ecommerceapp.products.core.port.inbound.commands.CreateProductCommand;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductResult;

public interface ProductHandler {
    CreateProductResult createProduct(CreateProductCommand command);
}
