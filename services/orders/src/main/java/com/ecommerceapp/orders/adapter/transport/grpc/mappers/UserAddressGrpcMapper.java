package com.ecommerceapp.orders.adapter.transport.grpc.mappers;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import com.ecommerceapp.generated.users.AddressResponse;
import com.ecommerceapp.orders.core.domain.entities.Address;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface UserAddressGrpcMapper {
    Address toAddressDomain(AddressResponse response);
}
