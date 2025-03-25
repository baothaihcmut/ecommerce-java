package com.ecommerceapp.libs.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public  class AppException  extends RuntimeException{
    private String message;
    private HttpStatus status;

    public AppException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
    }
}
