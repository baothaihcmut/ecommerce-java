package com.ecommerceapp.libs.events.orders;

public record OrderLineEvent(
        String id,
        String productItemId,
        int quantity,
        int subTotal) {
}
