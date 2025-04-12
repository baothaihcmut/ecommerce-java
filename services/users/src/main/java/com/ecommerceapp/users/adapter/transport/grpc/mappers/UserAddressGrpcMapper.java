package com.ecommerceapp.users.adapter.transport.grpc.mappers;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import com.ecommerceapp.generated.users.AddressResponse;
import com.ecommerceapp.generated.users.GetAddressByIdResponse;
import com.ecommerceapp.users.core.port.inbound.results.AddressResult;
import com.ecommerceapp.users.core.port.inbound.results.GetAddressByIdResult;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface UserAddressGrpcMapper {
    AddressResponse map(AddressResult result);

    GetAddressByIdResponse toGetAddresByIdResponse(GetAddressByIdResult result);
}
