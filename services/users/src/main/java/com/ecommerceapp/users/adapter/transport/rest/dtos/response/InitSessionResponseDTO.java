package com.ecommerceapp.users.adapter.transport.rest.dtos.response;

import java.time.Instant;

public record InitSessionResponseDTO(
        String sessionId,
        Instant expireAt) {

}
