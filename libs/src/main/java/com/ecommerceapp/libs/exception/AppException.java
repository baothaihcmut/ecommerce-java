package com.ecommerceapp.libs.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class AppException extends RuntimeException {
    private String message;
    private HttpStatus status;
    private Map<String, Object> details;

    public AppException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
        this.details = errorCode.getDetails();
    }

}
