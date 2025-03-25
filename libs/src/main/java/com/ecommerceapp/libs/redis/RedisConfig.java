package com.ecommerceapp.libs.redis;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {
    private final RedisProperties redisProperties;

    @Bean
    public RedissonClient redissionClient() {
        Config config = new Config();
        System.out.println(redisProperties.getHost());
        config.useSingleServer()
                .setAddress(String.format("redis://%s:%d", redisProperties.getHost(), redisProperties.getPort()));
        return org.redisson.Redisson.create(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}