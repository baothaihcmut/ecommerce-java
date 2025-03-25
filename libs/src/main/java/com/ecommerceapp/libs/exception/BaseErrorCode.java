package com.ecommerceapp.libs.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    String getMessage();
    HttpStatus getStatus();
}
