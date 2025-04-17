package com.ecommerceapp.orders.core.port.inbound.results;

import java.util.List;

import com.ecommerceapp.libs.response.PaginationResponse;

public record GetShopOrderLineResult(
        List<OrderLineResult> orderLineResults,
        PaginationResponse pagination) {

}
