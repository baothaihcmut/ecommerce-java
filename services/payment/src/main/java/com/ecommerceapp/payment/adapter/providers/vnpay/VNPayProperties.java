package com.ecommerceapp.payment.adapter.providers.vnpay;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.Data;

@ConfigurationProperties(prefix = "payment.vnpay")
@ConfigurationPropertiesScan
@Data
public class VNPayProperties {
    private String returnUrl;
    private String merchantCode;
    private String secureHash;
    private String version;
    private String vnpayUrl;

}
