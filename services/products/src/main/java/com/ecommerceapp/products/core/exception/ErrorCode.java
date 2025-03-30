package com.ecommerceapp.products.core.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.ecommerceapp.libs.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    DUPLICATE_PRODUCT_VARIATION(HttpStatus.CONFLICT, "Duplicate product variation", new HashMap<>()),
    SHOP_NOT_EXIST(HttpStatus.NOT_FOUND, "shop not exist", new HashMap<>()),
    USER_NOT_SHOP_OWNER(HttpStatus.FORBIDDEN, "user is not shop owner", new HashMap<>()),
    CATEGORY_NOT_EXIST(HttpStatus.NOT_FOUND, "category not exist", new HashMap<>()),
    USER_NOT_SHOP_OWNER_ACTIVE(HttpStatus.FORBIDDEN, "user haven't active shop owner", new HashMap<>()),
    PRODUCT_NOT_EXIST(HttpStatus.NOT_FOUND, "product not exist", new HashMap<>()),
    VARIATION_NOT_EXIST_IN_PRODUCT(HttpStatus.CONFLICT, "variation not exist in product", new HashMap<>());

    private HttpStatus status;
    private String message;
    private Map<String, Object> details;

    // Method to set dynamic details
    public ErrorCode withDetails(Map<String, Object> details) {
        this.details = details;
        return this;
    }

}
