package com.ecommerceapp.users.adapter.rest.mappers;

import org.mapstruct.Mapper;

import com.ecommerceapp.users.adapter.rest.dtos.request.SignUpRequestDTO;
import com.ecommerceapp.users.adapter.rest.dtos.response.SignUpResponseDTO;
import com.ecommerceapp.users.core.port.inbound.commands.SignUpCommand;
import com.ecommerceapp.users.core.port.inbound.results.SignUpResult;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    
    SignUpCommand toSignUpCommand(SignUpRequestDTO dto);

    SignUpResponseDTO toSignUpResponseDTO(SignUpResult result);
    
}
