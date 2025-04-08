package com.ecommerceapp.libs.docs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.Data;

@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "springdoc.info")
@Data
public class DocsInfoProperties {
    private String title;
    private String description;
    private String version;
}
