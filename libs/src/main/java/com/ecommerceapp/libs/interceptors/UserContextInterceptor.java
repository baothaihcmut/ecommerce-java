package com.ecommerceapp.libs.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ecommerceapp.libs.models.UserContext;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserContextInterceptor implements HandlerInterceptor {
    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userId = request.getHeader("X-User-Id");
        String isShopOwnerActive = request.getHeader("X-Is-Shop-Owner-Active");
        if (userId != null) {
            userContext.set(
                    new UserContext(
                            userId,
                            isShopOwnerActive != null && isShopOwnerActive.equals("true")));
        }
        return true;
    }

    public static UserContext getUserContext() {
        return userContext.get();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        userContext.remove();
    }
}
