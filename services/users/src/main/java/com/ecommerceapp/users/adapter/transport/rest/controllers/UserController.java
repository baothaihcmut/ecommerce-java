package com.ecommerceapp.users.adapter.transport.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.users.adapter.transport.rest.dtos.request.AddUserAddressRequestDTO;
import com.ecommerceapp.users.adapter.transport.rest.dtos.response.AddUserAddressResponseDTO;
import com.ecommerceapp.users.adapter.transport.rest.mappers.UserAddressMapper;
import com.ecommerceapp.users.core.port.inbound.handlers.UserAddressHandler;
import com.ecommerceapp.users.core.port.inbound.results.AddUserAddressResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserAddressHandler userAddressHandler;
    private final UserAddressMapper userAddressMapper;

    @PostMapping("/addresses/add")
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse<AddUserAddressResponseDTO> addUserAddress(
            @RequestBody @Valid AddUserAddressRequestDTO requestDTO) {
        AddUserAddressResult result = userAddressHandler.addUserAddress(
                userAddressMapper.toAddUserAddressCommand(requestDTO));
        return AppResponse.initResponse(
                true,
                "add user address success",
                userAddressMapper.toAddUserAddressResponseDTO(result));
    }
}
