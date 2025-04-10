package com.ecommerceapp.orders.core.port.inbound.results;

import com.ecommerceapp.orders.core.domain.entities.OrderLine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineResult {
    private String id;

    private String orderId;

    private String productItemId;

    private int quantity;

    private int subTotal;

    public static OrderLineResult toOrderLineResult(OrderLine orderLine, String orderId) {
        return OrderLineResult.builder()
                .id(orderLine.getId().toString())
                .orderId(orderId)
                .productItemId(orderLine.getProductItemId())
                .quantity(orderLine.getQuantity())
                .subTotal(orderLine.getSubTotal())
                .build();

    }
}
