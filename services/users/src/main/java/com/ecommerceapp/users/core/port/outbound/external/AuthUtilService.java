package com.ecommerceapp.users.core.port.outbound.external;

import java.util.UUID;

import com.ecommerceapp.users.core.port.outbound.external.models.AccessTokenPayload;


public interface AuthUtilService {
    String genAccessToken(AccessTokenPayload payload) throws Exception;
    String genRefreshToken(UUID userId) throws Exception;
}
