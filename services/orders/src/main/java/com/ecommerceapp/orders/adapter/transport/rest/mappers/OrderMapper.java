package com.ecommerceapp.orders.adapter.transport.rest.mappers;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import com.ecommerceapp.orders.adapter.transport.rest.dtos.request.CreateOrderRequestDTO;
import com.ecommerceapp.orders.adapter.transport.rest.dtos.response.CreateOrderResponseDTO;
import com.ecommerceapp.orders.adapter.transport.rest.dtos.response.OrderResponseDTO;
import com.ecommerceapp.orders.adapter.transport.rest.dtos.response.UpdateOrderResponseDTO;
import com.ecommerceapp.orders.core.port.inbound.commands.CreateOrderCommand;
import com.ecommerceapp.orders.core.port.inbound.commands.CreateOrderCommand.OrderItem;
import com.ecommerceapp.orders.core.port.inbound.results.CreateOrderResult;
import com.ecommerceapp.orders.core.port.inbound.results.OrderResult;
import com.ecommerceapp.orders.core.port.inbound.results.UpdateOrderAddressResult;

@Mapper(componentModel = "spring", uses = {
                OrderLineMapper.class }, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface OrderMapper {
        OrderItem map(
                        com.ecommerceapp.orders.adapter.transport.rest.dtos.request.CreateOrderRequestDTO.OrderItem orderItem);

        CreateOrderCommand toCreateOrderCommand(CreateOrderRequestDTO dto);

        OrderResponseDTO map(OrderResult result);

        CreateOrderResponseDTO toCreateOrderResponseDTO(CreateOrderResult result);

        UpdateOrderResponseDTO toUpdateOrderResponseDTO(UpdateOrderAddressResult result);
}
