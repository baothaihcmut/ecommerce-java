package com.ecommerceapp.orders.core.port.outbound.repositories;

import java.util.List;

import com.ecommerceapp.orders.core.domain.entities.OrderLine;
import com.ecommerceapp.orders.core.domain.enums.OrderStatus;

public interface OrderLineRepository {
    public record OrderLineAndCountResult(
            List<OrderLine> orderLines,
            long count) {

    }

    OrderLineAndCountResult findOrderLineOfShopAndCount(String shopId, OrderStatus status, Integer offset,
            Integer limit);
}
