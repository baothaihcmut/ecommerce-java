package com.ecommerceapp.orders.adapter.transport.rest.mappers;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import com.ecommerceapp.orders.adapter.transport.rest.dtos.response.OrderLineResponseDTO;
import com.ecommerceapp.orders.core.port.inbound.results.OrderLineResult;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface OrderLineMapper {

    OrderLineResponseDTO map(OrderLineResult result);

}
