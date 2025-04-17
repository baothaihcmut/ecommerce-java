package com.ecommerceapp.shops.core.port.inbound.results;

import java.time.Instant;
import java.util.List;

import com.ecommerceapp.libs.events.orders.OrderEvent.PaymentMethod;
import com.ecommerceapp.libs.response.PaginationResponse;
import com.ecommerceapp.shops.core.domain.enums.OrderStatus;
import com.ecommerceapp.shops.core.domain.enums.PaymentProvider;
import com.ecommerceapp.shops.core.domain.enums.ShipProvider;

public record GetOrderLinesOfShopResult(
        List<OrderLineInfoResult> orderLines,
        PaginationResponse pagination) {
    public record OrderLineInfoResult(
            String id,
            ProductInfo product,
            Integer quantity,
            Integer subTotal,
            OrderStatus status,
            PaymentMethod paymentMethod,
            PaymentProvider paymentProvider,
            ShipProvider shipProvider,
            Instant createdAt,
            Instant paidAt,
            Instant confirmedAt,
            Instant shippedAt,
            Instant recievedAt) {

    }

    public record ProductInfo(
            String id,
            String name,
            String thumbnail) {
    }
}
