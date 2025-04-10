package com.ecommerceapp.products.core.port.inbound.handlers;

import com.ecommerceapp.products.core.port.inbound.commands.CreateProductItemCommand;
import com.ecommerceapp.products.core.port.inbound.queries.GetProductItemWithProductQuery;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductItemResult;
import com.ecommerceapp.products.core.port.inbound.results.GetProductItemWithProductByIdListResult;

public interface ProductItemHandler {
    CreateProductItemResult createProductItem(CreateProductItemCommand command);

    GetProductItemWithProductByIdListResult getProductItemWithProductByIdList(GetProductItemWithProductQuery request);
}
