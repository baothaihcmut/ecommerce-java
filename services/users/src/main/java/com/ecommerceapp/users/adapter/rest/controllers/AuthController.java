package com.ecommerceapp.users.adapter.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.users.adapter.rest.dtos.request.ConfirmSignUpRequestDTO;
import com.ecommerceapp.users.adapter.rest.dtos.request.LogInRequestDTO;
import com.ecommerceapp.users.adapter.rest.dtos.request.SignUpRequestDTO;
import com.ecommerceapp.users.adapter.rest.mappers.AuthMapper;
import com.ecommerceapp.users.core.port.inbound.handlers.AuthHandler;
import com.ecommerceapp.users.core.port.inbound.results.LogInResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthHandler authHandler;
    private final AuthMapper authMapper;

    @PostMapping("/sign-up")
    public ResponseEntity<AppResponse> signUp(@RequestBody @Valid SignUpRequestDTO dto) throws Exception {
        this.authHandler.signUp(this.authMapper.toSignUpCommand(dto));
        return AppResponse.initResponse(
                HttpStatus.CREATED,
                true,
                "sign up success",
                null);
    }

    @PostMapping("/confirm")
    public ResponseEntity<AppResponse> confirmSignUp(@RequestBody @Valid ConfirmSignUpRequestDTO dto) throws Exception {
        this.authHandler.confirmSignUp(this.authMapper.toConfirmSignUpCommand(dto));
        return AppResponse.initResponse(
                HttpStatus.CREATED,
                true,
                "confirm sign up success",
                null);
    }

    @PostMapping("/log-in")
    public ResponseEntity<AppResponse> logIn(@RequestBody @Valid LogInRequestDTO dto) throws Exception {
        LogInResult res = this.authHandler.logIn(this.authMapper.toLogInCommand(dto));
        return AppResponse.initResponse(
                HttpStatus.CREATED,
                true,
                "log in success",
                this.authMapper.toLogInResponseDTO(res));
    }

}
