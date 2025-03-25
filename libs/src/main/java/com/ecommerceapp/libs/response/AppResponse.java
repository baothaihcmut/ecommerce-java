package com.ecommerceapp.libs.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class AppResponse {
    private boolean success;
    private String message;
    private Object data;

    public static ResponseEntity<AppResponse> initResponse(
            HttpStatus status, boolean success, String message, Object data) {
        return ResponseEntity.status(status)
                .body(AppResponse.builder().success(success).message(message).data(data).build());
    }
}
