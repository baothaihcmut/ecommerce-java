package com.ecommerceapp.orders.core.port.inbound.handlers;

import com.ecommerceapp.orders.core.port.inbound.commands.ConfirmOrderCommand;
import com.ecommerceapp.orders.core.port.inbound.commands.CreateOrderCommand;
import com.ecommerceapp.orders.core.port.inbound.commands.MakeOrderPaymentCommand;
import com.ecommerceapp.orders.core.port.inbound.commands.UpdateOrderAddressCommand;
import com.ecommerceapp.orders.core.port.inbound.queries.GetOrderStatusQuery;
import com.ecommerceapp.orders.core.port.inbound.results.ConfirmOrderResult;
import com.ecommerceapp.orders.core.port.inbound.results.CreateOrderResult;
import com.ecommerceapp.orders.core.port.inbound.results.GetOrderStatusResult;
import com.ecommerceapp.orders.core.port.inbound.results.MakeOrderPaymentResult;
import com.ecommerceapp.orders.core.port.inbound.results.UpdateOrderAddressResult;

public interface OrderHandler {
    CreateOrderResult createOrder(CreateOrderCommand command);

    UpdateOrderAddressResult updateOrderAddress(UpdateOrderAddressCommand command);

    MakeOrderPaymentResult makeOrderPayment(MakeOrderPaymentCommand command);

    ConfirmOrderResult confirmOrder(ConfirmOrderCommand command);

    GetOrderStatusResult getOrderStatus(GetOrderStatusQuery query);

    void cancelOrderOverdue();

}
