package com.ecommerceapp.libs.postgres;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableConfigurationProperties(PostgresProperties.class)
@ConditionalOnProperty(name = "spring.datasource.enabled", havingValue = "true", matchIfMissing = false)
@RequiredArgsConstructor
public class PostgresConfig {
    private final PostgresProperties postgresProperties;

    @Bean
    public DataSource dataSource() {
        HikariDataSource datasource = new HikariDataSource();
        datasource.setJdbcUrl(postgresProperties.getUrl());
        datasource.setUsername(postgresProperties.getUsername());
        datasource.setPassword(postgresProperties.getPassword());
        datasource.setDriverClassName(postgresProperties.getDriverClassName());
        return datasource;
    }
}
