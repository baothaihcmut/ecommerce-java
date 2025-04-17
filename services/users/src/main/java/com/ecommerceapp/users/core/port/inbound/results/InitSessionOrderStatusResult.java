package com.ecommerceapp.users.core.port.inbound.results;

import java.time.Instant;

public record InitSessionOrderStatusResult(
                String sessionId,
                Instant expireAt) {

}
