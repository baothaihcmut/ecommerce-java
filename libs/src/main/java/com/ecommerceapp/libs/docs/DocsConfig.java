package com.ecommerceapp.libs.docs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableConfigurationProperties(DocsInfoProperties.class)
@RequiredArgsConstructor
public class DocsConfig {
    private final DocsInfoProperties docsInfoProperties;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(
                new Info().title(docsInfoProperties.getTitle())
                        .description(docsInfoProperties.getDescription())
                        .version(docsInfoProperties.getVersion()));
    }
}
