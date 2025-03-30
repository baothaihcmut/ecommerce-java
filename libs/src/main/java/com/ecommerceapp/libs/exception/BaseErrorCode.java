package com.ecommerceapp.libs.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    String getMessage();

    HttpStatus getStatus();

    Map<String, Object> getDetails();

}
