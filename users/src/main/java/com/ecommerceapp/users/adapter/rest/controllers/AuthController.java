package com.ecommerceapp.users.adapter.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.users.adapter.rest.dtos.request.SignUpRequestDTO;
import com.ecommerceapp.users.adapter.rest.mappers.AuthMapper;
import com.ecommerceapp.users.core.port.inbound.handlers.AuthHandler;
import com.ecommerceapp.users.core.port.inbound.results.SignUpResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthHandler authHandler;
    private final AuthMapper authMapper;

    @PostMapping("/sign-up")
    public ResponseEntity<AppResponse> signUp(@RequestBody @Valid SignUpRequestDTO dto) throws Exception{
        SignUpResult res = this.authHandler.signUp(this.authMapper.toSignUpCommand(dto));
        return AppResponse.initResponse(
            HttpStatus.CREATED, 
            true, 
            "sign up success", 
            this.authMapper.toSignUpResponseDTO(res));
    }

    

}
