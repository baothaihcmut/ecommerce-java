package com.ecommerceapp.orders.core.port.inbound.handlers;

import com.ecommerceapp.orders.core.port.inbound.commands.CreateOrderCommand;
import com.ecommerceapp.orders.core.port.inbound.commands.MakeOrderPaymentCommand;
import com.ecommerceapp.orders.core.port.inbound.commands.UpdateOrderAddressCommand;
import com.ecommerceapp.orders.core.port.inbound.results.CreateOrderResult;
import com.ecommerceapp.orders.core.port.inbound.results.MakeOrderPaymentResult;
import com.ecommerceapp.orders.core.port.inbound.results.UpdateOrderAddressResult;

public interface OrderHandler {
    CreateOrderResult createOrder(CreateOrderCommand command);

    UpdateOrderAddressResult updateOrderAddress(UpdateOrderAddressCommand command);

    MakeOrderPaymentResult makeOrderPayment(MakeOrderPaymentCommand command);

    void cancelOrderOverdue();

}
