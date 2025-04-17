package com.ecommerceapp.users.adapter.transport.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.users.adapter.transport.rest.dtos.response.InitSessionResponseDTO;
import com.ecommerceapp.users.adapter.transport.rest.mappers.SessionMapper;
import com.ecommerceapp.users.core.port.inbound.commands.InitSessionOrderStatusCommand;
import com.ecommerceapp.users.core.port.inbound.handlers.SessionHandler;
import com.ecommerceapp.users.core.port.inbound.results.InitSessionOrderStatusResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/session")
@RequiredArgsConstructor
public class AuthSessionController {
    private final SessionHandler sessionHandler;
    private final SessionMapper sessionMapper;

    @PostMapping("/orders/{id}/status")
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse<InitSessionResponseDTO> initOrderStatusSession(@PathVariable String id) {
        InitSessionOrderStatusResult result = sessionHandler.initSessionOrderStatus(
                new InitSessionOrderStatusCommand(id));
        return AppResponse.initResponse(
                true,
                "init session of order status success",
                sessionMapper.toInitSessionResponseDTO(result));
    }
}
