package com.ecommerceapp.users.adapter.external;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.ecommerceapp.users.core.port.outbound.external.AuthUtilService;
import com.ecommerceapp.users.core.port.outbound.external.models.AccessTokenPayload;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@EnableConfigurationProperties(JwtProperties.class)
@RequiredArgsConstructor
public class AuthUtilServiceImpl implements AuthUtilService{
    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;
    private Key accessTokenKey;
    private Key refreshTokenKey;
    @PostConstruct
    public void init() {
        this.accessTokenKey = Keys.hmacShaKeyFor(this.jwtProperties.getAccessToken().getSecret().getBytes());
        this.refreshTokenKey = Keys.hmacShaKeyFor(this.jwtProperties.getRefreshToken().getSecret().getBytes());
    }
    @Override
    public String genAccessToken(AccessTokenPayload accessTokenPayload) throws Exception {
        return Jwts.builder()
                .setClaims(this.objectMapper.convertValue(accessTokenPayload, new TypeReference<Map<String, Object>>() {}))
                .setSubject(accessTokenPayload.getUserId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessToken().getExpiry()))
                .signWith(this.accessTokenKey,SignatureAlgorithm.HS256)
                .compact();
                
    }

    @Override
    public String genRefreshToken(UUID userId) throws Exception {
        return Jwts.builder()
                .setClaims(Map.of("userId",userId))
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshToken().getExpiry()))
                .signWith(this.refreshTokenKey,SignatureAlgorithm.HS256)
                .compact();
    }

   
    
}
