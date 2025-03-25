package com.ecommerceapp.users.adapter.external;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.ecommerceapp.users.core.port.outbound.external.AuthUtilService;
import com.ecommerceapp.users.core.port.outbound.external.models.AccessTokenPayload;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;

import lombok.RequiredArgsConstructor;

@Service
@EnableConfigurationProperties(JwtProperties.class)
@RequiredArgsConstructor
public class AuthUtilServiceImpl implements AuthUtilService{
    private final JwtProperties jwtProperties;

    @Override
    public String genAccessToken(AccessTokenPayload accessTokenPayload) throws Exception {
         JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
            .subject(accessTokenPayload.getUserId().toString())
            .expirationTime(
                new Date(
                    Instant.now()
                        .plus(
                            jwtProperties.getAccessToken().getExpiry(),
                            ChronoUnit.HOURS
                        )
                        .toEpochMilli()
                )
            )
            .claim("isShopOwnerActive", accessTokenPayload.getIsShopOwnerActive())
            .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        jwsObject.sign(
            new MACSigner(jwtProperties.getAccessToken().getSecret())
        );
        return jwsObject.serialize();
    }

    @Override
    public String genRefreshToken(UUID userId) throws Exception {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
            .subject(userId.toString())
            .expirationTime(
                new Date(
                    Instant.now()
                        .plus(
                            jwtProperties.getRefreshToken().getExpiry(),
                            ChronoUnit.HOURS
                        )
                        .toEpochMilli()
                )
            )
            .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        jwsObject.sign(
            new MACSigner(jwtProperties.getRefreshToken().getSecret())
        );
        return jwsObject.serialize();
    }

   
    
}
