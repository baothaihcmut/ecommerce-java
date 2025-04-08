package com.ecommerceapp.users.adapter.transport.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.libs.response.ErrorResponse;
import com.ecommerceapp.users.adapter.transport.rest.dtos.request.ConfirmSignUpRequestDTO;
import com.ecommerceapp.users.adapter.transport.rest.dtos.request.LogInRequestDTO;
import com.ecommerceapp.users.adapter.transport.rest.dtos.request.SignUpRequestDTO;
import com.ecommerceapp.users.adapter.transport.rest.dtos.response.LogInResponseDTO;
import com.ecommerceapp.users.adapter.transport.rest.mappers.AuthMapper;
import com.ecommerceapp.users.core.port.inbound.handlers.AuthHandler;
import com.ecommerceapp.users.core.port.inbound.results.LogInResult;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth")
public class AuthController {
    private final AuthHandler authHandler;
    private final AuthMapper authMapper;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sign up sucess"),
            @ApiResponse(responseCode = "409", description = "Email exist, Phone number exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse<Void> signUp(@RequestBody @Valid SignUpRequestDTO dto) throws Exception {
        this.authHandler.signUp(this.authMapper.toSignUpCommand(dto));
        return AppResponse.initResponse(
                true,
                "sign up success",
                null);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Confirm sign up sucess"),
            @ApiResponse(responseCode = "404", description = "Verification code not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/confirm")
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse<Void> confirmSignUp(@RequestBody @Valid ConfirmSignUpRequestDTO dto) throws Exception {
        this.authHandler.confirmSignUp(this.authMapper.toConfirmSignUpCommand(dto));
        return AppResponse.initResponse(
                true,
                "confirm sign up success",
                null);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Login sucess"),
            @ApiResponse(responseCode = "403", description = "Wrong email or password", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/log-in")
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse<LogInResponseDTO> logIn(@RequestBody @Valid LogInRequestDTO dto) throws Exception {
        LogInResult res = this.authHandler.logIn(this.authMapper.toLogInCommand(dto));
        return AppResponse.initResponse(
                true,
                "log in success",
                this.authMapper.toLogInResponseDTO(res));
    }

}
