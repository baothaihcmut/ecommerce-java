package com.ecommerceapp.libs.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static UserContext getUserContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserContext) {
            return (UserContext) authentication.getPrincipal();
        }
        return null;
    }
}
