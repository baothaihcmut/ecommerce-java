package com.ecommerceapp.libs.events.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineEvent {
    private String id;

    private String productItemId;

    private int quantity;

    private int subTotal;

}
