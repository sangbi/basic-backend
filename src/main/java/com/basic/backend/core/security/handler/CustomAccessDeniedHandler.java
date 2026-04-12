package com.basic.backend.core.security.handler;

import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.core.response.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> body = ApiResponse.err(
                ErrorCode.FORBIDDEN.code(),
                ErrorCode.FORBIDDEN.message()
        );
        response.setStatus(ErrorCode.FORBIDDEN.httpStatus().value());

        response.getWriter().write(objectMapper.writeValueAsString(body));

        log.warn(
                "[SECURITY] type=FORBIDDEN uri={} method={} message={}",
                request.getRequestURI(),
                request.getMethod(),
                accessDeniedException.getMessage(),
                accessDeniedException
        );
    }
}