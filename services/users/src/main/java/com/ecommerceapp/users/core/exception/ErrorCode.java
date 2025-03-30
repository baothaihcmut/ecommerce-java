package com.ecommerceapp.users.core.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.ecommerceapp.libs.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    BAD_CREDENTIALS_EXCEPTION(HttpStatus.UNAUTHORIZED, "Email or password is incorrect", new HashMap<>()),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found", new HashMap<>()),
    EMAIL_EXIST(HttpStatus.CONFLICT, "email exist", new HashMap<>()),
    PHONE_NUMBER_EXIST(HttpStatus.CONFLICT, "phone number exist", new HashMap<>()),
    USER_PENDING_VERFICATION_SIGN_UP(HttpStatus.CONFLICT, "user is pending for verification", new HashMap<>()),
    WRONG_VERIFICATION_CODE(HttpStatus.UNAUTHORIZED, "wrong verification code", new HashMap<>());

    private HttpStatus status;
    private String message;
    private Map<String, Object> details;

    @Override
    public Map<String, Object> getDetails() {
        return this.details;
    }
}
