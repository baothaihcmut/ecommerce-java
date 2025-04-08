package com.ecommerceapp.libs.mongo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "spring.data.mongodb")
@ConfigurationPropertiesScan
@Data
public class MongoProperties {
    private String uri;
    private String database;

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
