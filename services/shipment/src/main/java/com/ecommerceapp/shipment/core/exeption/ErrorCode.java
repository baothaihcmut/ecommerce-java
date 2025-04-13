package com.ecommerceapp.shipment.core.exeption;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.ecommerceapp.libs.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    SHOP_NOT_EXIST(HttpStatus.NOT_FOUND, "shop not exist", new HashMap<>()),
    SHIP_PROVIDER_NOT_SUPPORT(HttpStatus.NOT_FOUND, "ship provider not supported", new HashMap<>());

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
