package com.team5.backend.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private static final List<String> ALLOWED_PATHS = Arrays.asList("/member/*", "/token");
    private final JWTUtil jwtUtil;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String authHeader = request.getHeader("Authorization");

        // 인증이 필요하지 않은 API 통과
        String path = request.getRequestURI();
        if (ALLOWED_PATHS.contains(path)) {
            return true;
        }

        // Authorization 헤더 검증
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"code\": 401, \"message\": \"Authorization header is missing or invalid\"}");
            response.setContentType("application/json");
            return false;
        }

        String token = authHeader.substring(7);

        // 토큰 검증 로직
        String username = jwtUtil.getUsername(token);
        if (username == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"code\": 401, \"message\": \"Invalid token\"}");
            response.setContentType("application/json");
            return false;
        }

        // 사용자 정보를 RequestAttributes에 저장
        Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).setAttribute("username", username, RequestAttributes.SCOPE_REQUEST);

        return true;
    }

}
