package com.ecommerceapp.libs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private boolean success;
    private String message;
    private Object details;

    public static ErrorResponse initResponse(
            boolean success, String message, Object details) {
        return new ErrorResponse(success, message, details);
    }
}
