package com.ecommerceapp.libs.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.Data;

@ConfigurationProperties(prefix = "aws.s3")
@ConfigurationPropertiesScan
@Data
public class S3Properties {
    private String accessKey;
    private String secretKey;
    private String region;
    private String bucket;
}
