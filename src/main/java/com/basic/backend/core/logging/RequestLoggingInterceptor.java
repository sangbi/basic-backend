package com.basic.backend.core.logging;

import com.basic.backend.core.auth.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        long startTime = System.currentTimeMillis();
        String traceId = UUID.randomUUID().toString();

        request.setAttribute(RequestContext.START_TIME, startTime);
        request.setAttribute(RequestContext.TRACE_ID, traceId);

        log.info(
                "[REQUEST] traceId={} method={} uri={} userId={}",
                traceId,
                request.getMethod(),
                request.getRequestURI(),
                SecurityUtil.getCurrentUserId()
        );

        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            @Nullable Exception ex
    ) {
        Object startTimeAttr = request.getAttribute(RequestContext.START_TIME);
        Object traceIdAttr = request.getAttribute(RequestContext.TRACE_ID);

        long durationMs = 0L;
        String traceId = traceIdAttr == null ? null : traceIdAttr.toString();

        if (startTimeAttr instanceof Long startTime) {
            durationMs = System.currentTimeMillis() - startTime;
        }

        if (ex != null) {
            log.error(
                    "[ERROR] traceId={} method={} uri={} status={} durationMs={} userId={} exception={} message={}",
                    traceId,
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    durationMs,
                    SecurityUtil.getCurrentUserId(),
                    ex.getClass().getSimpleName(),
                    ex.getMessage(),
                    ex
            );
            return;
        }

        log.info(
                "[RESPONSE] traceId={} method={} uri={} status={} durationMs={} userId={}",
                traceId,
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                durationMs,
                SecurityUtil.getCurrentUserId()
        );
    }
}