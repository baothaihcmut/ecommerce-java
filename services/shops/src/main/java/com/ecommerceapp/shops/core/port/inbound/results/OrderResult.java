package com.ecommerceapp.shops.core.port.inbound.results;

import java.time.Instant;

import com.ecommerceapp.shops.core.domain.enums.OrderStatus;
import com.ecommerceapp.shops.core.domain.enums.PaymentMethod;
import com.ecommerceapp.shops.core.domain.enums.PaymentProvider;
import com.ecommerceapp.shops.core.domain.enums.ShipProvider;

public record OrderResult(
        String id,
        String userId,
        OrderStatus status,
        PaymentMethod paymentMethod,
        PaymentProvider paymentProvider,
        ShipProvider shipProvider,
        Instant createdAt,
        Instant canceledAt,
        Instant paidAt,
        Instant confirmedAt,
        Instant shippedAt,
        Instant recievedAt) {

}
