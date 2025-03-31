package com.ecommerceapp.libs.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public record UserContext(String userId, Boolean isShopOwnerActive) {
    }

    public static UserContext getUserContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
            return new UserContext(details.getUserId(), details.getIsShopOwnerActive());
        }
        return null;
    }
}
