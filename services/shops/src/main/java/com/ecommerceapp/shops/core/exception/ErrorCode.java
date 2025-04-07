package com.ecommerceapp.shops.core.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.ecommerceapp.libs.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    USER_NOT_SHOP_OWNER_ACTIVE(HttpStatus.FORBIDDEN, "user haven't active shop owner", new HashMap<>()),
    SHOP_NOT_EXIST(HttpStatus.NOT_FOUND, "shop not exist", new HashMap<>()),
    SHOP_NOT_BELONG_TO_USER(HttpStatus.FORBIDDEN, "shop not belong to user", new HashMap<>()),
    USER_NOT_FOLLOW_SHOP(HttpStatus.CONFLICT, "user is not follower of shop", new HashMap<>()),
    SHOP_CATEGORY_NOT_EXIST(HttpStatus.NOT_FOUND, "shop category not exist", new HashMap<>());

    private HttpStatus status;
    private String message;
    private Map<String, Object> details;

    @Override
    public Map<String, Object> getDetails() {
        return this.details;
    }
}
