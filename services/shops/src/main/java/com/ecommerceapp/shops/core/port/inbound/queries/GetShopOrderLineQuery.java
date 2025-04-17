package com.ecommerceapp.shops.core.port.inbound.queries;

import com.ecommerceapp.libs.queries.PaginationQuery;
import com.ecommerceapp.shops.core.domain.enums.OrderStatus;

public record GetShopOrderLineQuery(
        String id,
        OrderStatus status,
        PaginationQuery paginationQuery) {

}
