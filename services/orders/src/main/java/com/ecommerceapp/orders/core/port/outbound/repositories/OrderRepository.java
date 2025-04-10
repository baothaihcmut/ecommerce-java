package com.ecommerceapp.orders.core.port.outbound.repositories;

import com.ecommerceapp.orders.core.domain.entities.Order;

public interface OrderRepository {
    void save(Order order);

}
