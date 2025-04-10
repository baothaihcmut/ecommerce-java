package com.ecommerceapp.products.core.port.inbound.handlers;

import com.ecommerceapp.products.core.port.inbound.commands.DecreaseProductItemStockCommand;
import com.ecommerceapp.products.core.port.inbound.commands.IncreaseProductItemStockCommand;
import com.ecommerceapp.products.core.port.inbound.results.DecreaseProductItemStockResult;
import com.ecommerceapp.products.core.port.inbound.results.IncreaseProductItemStockResult;

public interface ProductItemStockHandler {
    IncreaseProductItemStockResult increaseProductItemStock(IncreaseProductItemStockCommand command);

    DecreaseProductItemStockResult decreaseProductItemStock(DecreaseProductItemStockCommand command);
}
