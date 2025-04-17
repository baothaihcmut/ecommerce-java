package com.ecommerceapp.users.adapter.transport.rest.mappers;

import org.mapstruct.Mapper;

import com.ecommerceapp.users.adapter.transport.rest.dtos.response.InitSessionResponseDTO;
import com.ecommerceapp.users.core.port.inbound.results.InitSessionOrderStatusResult;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    InitSessionResponseDTO toInitSessionResponseDTO(InitSessionOrderStatusResult result);
}
