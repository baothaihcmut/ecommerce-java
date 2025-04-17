package com.ecommerceapp.orders.adapter.transport.queue.mappers;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ecommerceapp.libs.events.orders.BulkOrderCanceledEvent;
import com.ecommerceapp.libs.events.orders.OrderCanceledEvent;
import com.ecommerceapp.libs.events.orders.OrderCreatedEvent;
import com.ecommerceapp.libs.events.orders.OrderEvent;
import com.ecommerceapp.libs.events.orders.OrderLineEvent;
import com.ecommerceapp.libs.events.orders.OrderPaidEvent;
import com.ecommerceapp.libs.events.orders.OrderEvent.OrderStatus;
import com.ecommerceapp.libs.events.orders.OrderEvent.PaymentMethod;
import com.ecommerceapp.libs.mappers.CommonMapper;
import com.ecommerceapp.orders.core.domain.entities.Order;
import com.ecommerceapp.orders.core.domain.entities.OrderLine;
import com.ecommerceapp.orders.core.domain.events.OrderCancelEvent;
import com.ecommerceapp.orders.core.domain.events.OrderConfirmedEvent;

@Mapper(componentModel = "spring", uses = {
                CommonMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface OrderEventMapper {
        OrderStatus map(com.ecommerceapp.orders.core.domain.enums.OrderStatus domain);

        PaymentMethod map(com.ecommerceapp.orders.core.domain.enums.PaymentMethod domain);

        @Mapping(source = "id", target = "id", qualifiedByName = "mapUUIDToString")
        OrderLineEvent map(OrderLine orderLine);

        @Mapping(source = "id", target = "id", qualifiedByName = "mapUUIDToString")
        OrderEvent map(Order order);

        OrderCreatedEvent toOrderCreatedEvent(com.ecommerceapp.orders.core.domain.events.OrderCreatedEvent domainEvent);

        OrderPaidEvent toOrderPaidEvent(com.ecommerceapp.orders.core.domain.events.OrderPaidEvent orderPaidEvent);

        OrderCanceledEvent toOrderCanceledEvent(OrderCancelEvent event);

        com.ecommerceapp.libs.events.orders.OrderConfirmedEvent toOrderConfirmedEvent(OrderConfirmedEvent event);

        BulkOrderCanceledEvent toBulkOrderCanceledEvent(
                        com.ecommerceapp.orders.core.domain.events.BulkOrderCanceledEvent domain);
}
