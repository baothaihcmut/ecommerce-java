package com.ecommerceapp.orders.core.port.inbound.queries;

import com.ecommerceapp.libs.queries.PaginationQuery;
import com.ecommerceapp.orders.core.domain.enums.OrderStatus;

public record GetShopOrderLineQuery(
        String shopId,
        OrderStatus status,
        PaginationQuery pagination) {

}
