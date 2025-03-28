package com.ecommerceapp.libs.exception;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.ecommerceapp.libs.response.AppResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<AppResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {

        return AppResponse.initResponse(HttpStatus.BAD_REQUEST, false, exception.getMessage(), null);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<AppResponse> handleAppException(AppException exception) {

        return AppResponse.initResponse(exception.getStatus(), false, exception.getMessage(), exception.getDetails());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<AppResponse> handleMethodArgumentNotValidException(AccessDeniedException exception) {

        return AppResponse.initResponse(HttpStatus.FORBIDDEN, false,
                "You don't have permission to access this resource", null);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    ResponseEntity<AppResponse> handleMethodArgumentNotValidException(
            HttpRequestMethodNotSupportedException exception) {

        return AppResponse.initResponse(HttpStatus.METHOD_NOT_ALLOWED, false, "Method not allow", null);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    ResponseEntity<AppResponse> handleMessageNotReadableException(
            HttpRequestMethodNotSupportedException exception) {
        return AppResponse.initResponse(HttpStatus.BAD_REQUEST, false, "Missing request body", null);
    }

    // Method Not Allowed
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<AppResponse> handleRuntimeException(RuntimeException exception) {
        System.out.println("Errorrrr");
        System.out.println(exception);
        return AppResponse.initResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Internal error", null);
    }

    // IllegalStateException
    @ExceptionHandler(value = NoResourceFoundException.class)
    ResponseEntity<AppResponse> handleNoResourceFoundException(
            NoResourceFoundException exception) {
        return AppResponse.initResponse(HttpStatus.NOT_FOUND, false, "Resource not found", null);
    }
}