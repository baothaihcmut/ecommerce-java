package com.ecommerceapp.libs.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class ExtractUserInfoFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String userId = httpRequest.getHeader("X-User-ID");
        String isShopOwnerActive = httpRequest.getHeader("X-Is-Shop-Owner-Active");
        if (userId != null && isShopOwnerActive != null) {
            CustomUserDetails userContext = new CustomUserDetails(userId, isShopOwnerActive.equals("true"));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userContext,
                    null,
                    new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
