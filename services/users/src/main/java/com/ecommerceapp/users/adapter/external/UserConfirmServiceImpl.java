package com.ecommerceapp.users.adapter.external;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.redis.RedisService;
import com.ecommerceapp.users.core.domain.entities.User;
import com.ecommerceapp.users.core.port.outbound.external.UserConfirmService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserConfirmServiceImpl implements UserConfirmService {
    private final RedisService redisService;

    @Override
    public Optional<User> getUser(String code) throws Exception {
        User res = redisService.getValueObject(String.format("email_verfication_code:%s", code), User.class);
        if (res == null) {
            return Optional.empty();
        }
        // remove key
        this.redisService.removeByKey(String.format("email_verfication_code:%s", code));
        this.redisService.removeByKey(String.format("email_pending_verification:%s", res.getEmail()));
        return Optional.of(res);
    }

    public String storeUser(User user) throws Exception {
        // store email in pending
        redisService.setString(String.format("email_pending_verification:%s", user.getEmail()), "1", 30,
                TimeUnit.MINUTES);
        // geneate code
        String code = UUID.randomUUID().toString();
        redisService.setObject(String.format("email_verfication_code:%s", code), user, 30, TimeUnit.MINUTES);
        return String.format("http://localhost:3000/homepage?code=%s", code);
    }

    @Override
    public boolean isUserPendingConfirm(String email) throws Exception {
        return redisService.getValueString(String.format("email_pending_verification:%s", email)) != null;
    }

}
