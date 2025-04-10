package com.ecommerceapp.orders.core.port.inbound.handlers;

import com.ecommerceapp.orders.core.port.inbound.commands.CreateOrderCommand;
import com.ecommerceapp.orders.core.port.inbound.results.CreateOrderResult;

public interface OrderHandler {
    CreateOrderResult createOrder(CreateOrderCommand command);
}
