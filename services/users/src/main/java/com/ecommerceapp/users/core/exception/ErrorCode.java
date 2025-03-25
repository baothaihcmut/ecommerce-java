package com.ecommerceapp.users.core.exception;

import org.springframework.http.HttpStatus;

import com.ecommerceapp.libs.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    BAD_CREDENTIALS_EXCEPTION(HttpStatus.UNAUTHORIZED, "Email or password is incorrect"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    EMAIL_EXIST(HttpStatus.CONFLICT, "email exist"),
    PHONE_NUMBER_EXIST(HttpStatus.CONFLICT, "phone number exist"),
    USER_PENDING_VERFICATION_SIGN_UP(HttpStatus.CONFLICT, "user is pending for verification"),
    WRONG_VERIFICATION_CODE(HttpStatus.UNAUTHORIZED, "wrong verification code");

    private HttpStatus status;
    private String message;
}
