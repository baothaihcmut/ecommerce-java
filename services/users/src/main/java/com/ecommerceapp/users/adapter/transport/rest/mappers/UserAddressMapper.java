package com.ecommerceapp.users.adapter.transport.rest.mappers;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import com.ecommerceapp.users.adapter.transport.rest.dtos.request.AddUserAddressRequestDTO;
import com.ecommerceapp.users.adapter.transport.rest.dtos.response.AddUserAddressResponseDTO;
import com.ecommerceapp.users.adapter.transport.rest.dtos.response.AddressResponseDTO;
import com.ecommerceapp.users.core.port.inbound.commands.AddUserAddressCommand;
import com.ecommerceapp.users.core.port.inbound.results.AddUserAddressResult;
import com.ecommerceapp.users.core.port.inbound.results.AddressResult;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface UserAddressMapper {
    AddUserAddressCommand toAddUserAddressCommand(AddUserAddressRequestDTO dto);

    AddressResponseDTO map(AddressResult result);

    AddUserAddressResponseDTO toAddUserAddressResponseDTO(AddUserAddressResult result);
}
