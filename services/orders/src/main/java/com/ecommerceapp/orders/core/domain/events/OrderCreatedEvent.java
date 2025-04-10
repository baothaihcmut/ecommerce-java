package com.ecommerceapp.orders.core.domain.events;

import com.ecommerceapp.orders.core.domain.entities.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreatedEvent {
    private Order order;
}
