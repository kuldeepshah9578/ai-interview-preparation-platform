package com.interview.ai_platform.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    private static final String API_KEY = "my-secret-key";

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        String requestKey = request.getHeader("x-api-key");

        if (API_KEY.equals(requestKey)) {
            return true;
        }

        response.setStatus(401);
        response.getWriter().write("Invalid or missing API key");

        return false;
    }
}