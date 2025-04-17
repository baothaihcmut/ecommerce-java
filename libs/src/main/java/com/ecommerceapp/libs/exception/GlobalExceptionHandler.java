package com.ecommerceapp.libs.exception;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.ecommerceapp.libs.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {

        return ResponseEntity.badRequest()
                .body(ErrorResponse.initResponse(false, exception.getLocalizedMessage(), null));
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ErrorResponse> handleAppException(AppException exception) {

        return ResponseEntity.status(exception.getStatus())
                .body(ErrorResponse.initResponse(false, exception.getMessage(), null));
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(AccessDeniedException exception) {

        return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(ErrorResponse.initResponse(false,
                "You don't have permission to access this resource", null));
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            HttpRequestMethodNotSupportedException exception) {

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ErrorResponse.initResponse(false, "Method not allow", null));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    ResponseEntity<ErrorResponse> handleMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.initResponse(false, "Missing request body", null));
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    ResponseEntity<ErrorResponse> handleNoResourceFoundException(
            NoResourceFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.initResponse(false, "Resource not found", null));
    }

    // Method Not Allowed
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ErrorResponse> handleRuntimeException(Exception exception) {
        exception.printStackTrace(); // Log the exception properly
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.initResponse(false, "Internal error", null));
    }
}