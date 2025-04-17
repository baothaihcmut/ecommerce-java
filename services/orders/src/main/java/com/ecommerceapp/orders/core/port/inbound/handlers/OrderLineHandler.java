package com.ecommerceapp.orders.core.port.inbound.handlers;

import com.ecommerceapp.orders.core.port.inbound.queries.GetShopOrderLineQuery;
import com.ecommerceapp.orders.core.port.inbound.results.GetShopOrderLineResult;

public interface OrderLineHandler {
    GetShopOrderLineResult getShopOrderLines(GetShopOrderLineQuery query);
}
