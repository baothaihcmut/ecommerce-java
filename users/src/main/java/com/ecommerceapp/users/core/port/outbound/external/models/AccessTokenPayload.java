package com.ecommerceapp.users.core.port.outbound.external.models;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class AccessTokenPayload {
    private UUID userId;
    private Boolean isShopOwnerActive;

}
