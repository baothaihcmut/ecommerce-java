package com.ecommerceapp.shops.core.port.inbound.handlers;

import com.ecommerceapp.shops.core.port.inbound.queries.GetShopOrderLineQuery;
import com.ecommerceapp.shops.core.port.inbound.results.GetOrderLinesOfShopResult;

public interface ShopOrderLineHandler {
    GetOrderLinesOfShopResult getOrderLineOfShop(GetShopOrderLineQuery query);
}
