package com.ecommerceapp.libs.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> AppResponse<T> initResponse(
            boolean success, String message, T data) {
        return new AppResponse<T>(success, message, data);
    }
}
