package com.ecommerceapp.products.core.port.inbound.handlers;

import com.ecommerceapp.products.core.port.inbound.commands.CreateProductItemCommand;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductItemResult;

public interface ProductItemHandler {
    CreateProductItemResult createProductItem(CreateProductItemCommand command);
}
