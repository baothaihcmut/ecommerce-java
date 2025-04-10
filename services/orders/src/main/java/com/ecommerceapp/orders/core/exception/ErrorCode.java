package com.ecommerceapp.orders.core.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.ecommerceapp.libs.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    ORDER_IS_NOT_PENDING(HttpStatus.CONFLICT, "order is not pending status", new HashMap<>()),
    ORDER_IS_NOT_CONFIRMED(HttpStatus.CONFLICT, "order is not confirmed status", new HashMap<>()),
    ORDER_IS_NOT_SHIPPED(HttpStatus.CONFLICT, "order is not shipped status", new HashMap<>()),
    ORDER_IS_NOT_BELONG_TO_USER(HttpStatus.FORBIDDEN, "order is not belong to user", new HashMap<>()),
    ORDER_LINE_EQUAL_ZERO(HttpStatus.CONFLICT, "order must have at least 1 item", new HashMap<>()),
    ORDER_LINE_IS_IN_DIFFERENT_SHOP(HttpStatus.CONFLICT, "order line must be in same shop", new HashMap<>()),
    PRODUCT_ITEM_OUT_OF_STOCK(HttpStatus.CONFLICT, "product item out of stock", new HashMap<>());
    ;

    private HttpStatus status;
    private String message;
    private Map<String, Object> details;

    @Override
    public Map<String, Object> getDetails() {
        return this.details;
    }

    public BaseErrorCode withDetails(Map<String, Object> details) {
        this.details = details;
        return this;
    }
}
