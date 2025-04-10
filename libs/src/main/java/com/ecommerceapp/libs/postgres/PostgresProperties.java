package com.ecommerceapp.libs.postgres;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "spring.datasource")
@ConfigurationPropertiesScan
@Data
public class PostgresProperties {
    private String url;
    private String username;
    private String password;
    private String driverClassName;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConnectionPool {
        private Integer maxConnection;
        private Integer minConnection;
        private Integer maxConnectionIdleTime;
    }

    private ConnectionPool connectionPool;

}