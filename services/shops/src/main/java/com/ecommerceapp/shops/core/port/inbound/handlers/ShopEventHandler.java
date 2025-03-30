package com.ecommerceapp.shops.core.port.inbound.handlers;

import com.ecommerceapp.libs.events.products.ProductAdditionEvent;

public interface ShopEventHandler {
    void handleAddProduct(ProductAdditionEvent event);
}
