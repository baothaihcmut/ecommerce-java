package com.ecommerceapp.users.adapter.rest.mappers;

import org.mapstruct.Mapper;

import com.ecommerceapp.users.adapter.rest.dtos.request.ConfirmSignUpRequestDTO;
import com.ecommerceapp.users.adapter.rest.dtos.request.LogInRequestDTO;
import com.ecommerceapp.users.adapter.rest.dtos.request.SignUpRequestDTO;
import com.ecommerceapp.users.adapter.rest.dtos.response.LogInResponseDTO;
import com.ecommerceapp.users.core.port.inbound.commands.ConfirmSignUpCommand;
import com.ecommerceapp.users.core.port.inbound.commands.LogInCommand;
import com.ecommerceapp.users.core.port.inbound.commands.SignUpCommand;
import com.ecommerceapp.users.core.port.inbound.results.LogInResult;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    SignUpCommand toSignUpCommand(SignUpRequestDTO dto);

    ConfirmSignUpCommand toConfirmSignUpCommand(ConfirmSignUpRequestDTO dto);

    LogInCommand toLogInCommand(LogInRequestDTO dto);

    LogInResponseDTO toLogInResponseDTO(LogInResult result);

}
