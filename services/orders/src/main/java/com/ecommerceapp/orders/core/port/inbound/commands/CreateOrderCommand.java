package com.ecommerceapp.orders.core.port.inbound.commands;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderCommand {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItem {
        private String productItemId;
        private Integer quantity;
    }

    private List<OrderItem> orderItems;
}
