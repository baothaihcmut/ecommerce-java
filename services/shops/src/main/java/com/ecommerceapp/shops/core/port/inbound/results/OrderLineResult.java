package com.ecommerceapp.shops.core.port.inbound.results;

public record OrderLineResult(
        String id,
        String productItemId,
        String quantity,
        String subTotal) {

}
