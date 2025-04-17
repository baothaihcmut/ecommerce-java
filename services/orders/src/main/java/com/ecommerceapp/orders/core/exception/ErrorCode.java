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
    ORDER_NOT_EXIT(HttpStatus.NOT_FOUND, "order is not exist", new HashMap<>()),
    ORDER_IS_NOT_PENDING(HttpStatus.CONFLICT, "order is not pending status", new HashMap<>()),
    ORDER_IS_NOT_PAID(HttpStatus.CONFLICT, "order is not paid", new HashMap<>()),
    ORDER_IS_NOT_CONFIRMED(HttpStatus.CONFLICT, "order is not confirmed status", new HashMap<>()),
    ORDER_IS_NOT_SHIPPED(HttpStatus.CONFLICT, "order is not shipped status", new HashMap<>()),
    ORDER_IS_NOT_PROCESS_PAYMENT(HttpStatus.CONFLICT, "order is not in payment process", new HashMap<>()),
    ORDER_IS_NOT_BELONG_TO_USER(HttpStatus.FORBIDDEN, "order is not belong to user", new HashMap<>()),
    ORDER_LINE_EQUAL_ZERO(HttpStatus.CONFLICT, "order must have at least 1 item", new HashMap<>()),
    ORDER_LINE_IS_IN_DIFFERENT_SHOP(HttpStatus.CONFLICT, "order line must be in same shop", new HashMap<>()),
    PRODUCT_ITEM_OUT_OF_STOCK(HttpStatus.CONFLICT, "product item out of stock", new HashMap<>()),
    INVALID_ORDER_RECEIVEADDRESS(HttpStatus.FORBIDDEN, "invalid order address", new HashMap<>()),
    PAYMENT_PROVIDER_IS_REQUIRED(HttpStatus.BAD_REQUEST, "payment provider is required with advance payment",
            new HashMap<>()),
    USER_IS_NOT_SHOP_OWNER_OF_ORDER(HttpStatus.FORBIDDEN, "only shop owner can confirm shop's order", new HashMap<>()),
    USER_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "user don't have permission", new HashMap<>()),
    ORDER_STATUS_SESSION_NOT_FOUND_OR_EXPIRE(HttpStatus.UNAUTHORIZED, "order status session not found or expire",
            new HashMap<>());

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
