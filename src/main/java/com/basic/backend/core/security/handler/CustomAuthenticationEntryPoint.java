package com.basic.backend.core.security.handler;

import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.core.response.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> body = ApiResponse.err(
                ErrorCode.UNAUTHORIZED.code(),
                ErrorCode.UNAUTHORIZED.message()
        );
        response.setStatus(ErrorCode.UNAUTHORIZED.httpStatus().value());

        response.getWriter().write(objectMapper.writeValueAsString(body));

        log.warn(
                "[SECURITY] type=UNAUTHORIZED uri={} method={} message={}",
                request.getRequestURI(),
                request.getMethod(),
                authException.getMessage(),
                authException
        );
    }
}