package com.ecommerceapp.orders.core.port.outbound.repositories;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ecommerceapp.orders.core.domain.entities.Order;

public interface OrderRepository {
    void save(Order order);

    void bulkCancelOrder(List<Order> orders);

    List<Order> findOrderWithOrderLinesByCreatedAtBeforeTime(Instant time);

    Optional<Order> findOrderById(UUID id);

}
