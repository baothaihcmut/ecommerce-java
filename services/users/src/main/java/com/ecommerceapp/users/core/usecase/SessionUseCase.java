package com.ecommerceapp.users.core.usecase;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.models.SessionOrderStatusInfo;
import com.ecommerceapp.libs.redis.RedisService;
import com.ecommerceapp.libs.security.SecurityUtil;
import com.ecommerceapp.libs.security.SecurityUtil.UserContext;
import com.ecommerceapp.users.core.port.inbound.commands.InitSessionOrderStatusCommand;
import com.ecommerceapp.users.core.port.inbound.handlers.SessionHandler;
import com.ecommerceapp.users.core.port.inbound.results.InitSessionOrderStatusResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionUseCase implements SessionHandler {
    private final RedisService redisService;
    private static final SecureRandom secureRandom = new SecureRandom(); // thread-safe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();
    private static final String orderStatusSessionCacheKey = "session:orders:status";

    private String generateSessionId() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    @Override
    public InitSessionOrderStatusResult initSessionOrderStatus(InitSessionOrderStatusCommand command) {
        // get user context
        UserContext userContext = SecurityUtil.getUserContext();
        String sessionID = generateSessionId();
        try {
            redisService.setObject(
                    String.format("%s:%s", orderStatusSessionCacheKey, sessionID),
                    new SessionOrderStatusInfo(userContext.userId(), command.orderId()),
                    30, TimeUnit.MINUTES);
            return new InitSessionOrderStatusResult(sessionID, Instant.now().plus(30, ChronoUnit.MINUTES));
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }

    }

}
