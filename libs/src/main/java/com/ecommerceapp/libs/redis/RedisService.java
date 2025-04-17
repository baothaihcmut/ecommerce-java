package com.ecommerceapp.libs.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.data.redis.enabled", havingValue = "true", matchIfMissing = false)
public class RedisService {
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate redisTemplate;

    public <T> void setObject(String key, T value, long ttl, TimeUnit unit) throws Exception {
        String obj = objectMapper.writeValueAsString(value);
        redisTemplate.opsForValue().set(key, obj, ttl, unit);
    }

    public void setString(String key, String value, long ttl, TimeUnit unit) throws Exception {
        redisTemplate.opsForValue().set(key, value, ttl, unit);
    }

    public String getValueString(String key) throws Exception {
        return redisTemplate.opsForValue().get(key);
    }

    public <T> T getValueObject(String key, Class<T> clazz) throws Exception {
        String res = redisTemplate.opsForValue().get(key);
        return res != null ? objectMapper.readValue(res, clazz) : null;
    }

    public void removeByKey(String key) throws Exception {
        redisTemplate.delete(key);
    }

    public boolean existByKey(String key) throws Exception {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
