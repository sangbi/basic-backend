package com.basic.backend.core.logging;

import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.core.util.RequestInfoUtil;
import com.basic.backend.domain.service.ActivityLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ActivityLogAspect {

    private final ActivityLogService activityLogService;
    private final HttpServletRequest httpRequest;

    public ActivityLogAspect(
            ActivityLogService activityLogService,
            HttpServletRequest httpRequest) {
        this.activityLogService = activityLogService;
        this.httpRequest = httpRequest;
    }

    @Around("@annotation(activityLogAction)")
    public Object logActivity(
            ProceedingJoinPoint joinPoint,
            ActivityLogAction activityLogAction
    ) throws Throwable {

        try{
            Object result = joinPoint.proceed();

            String message = buildMessage(activityLogAction, joinPoint, result);

            activityLogService.save(
                    SecurityUtil.getCurrentUserId(),
                    activityLogAction.actionType(),
                    httpRequest.getMethod(),
                    httpRequest.getRequestURI(),
                    RequestInfoUtil.getClientIp(httpRequest),
                    RequestInfoUtil.getUserAgent(httpRequest),
                    "200",
                    message
            );

            return result;
        } catch (Throwable e) {
            String message = activityLogAction.message() + " | error=" + e.getMessage();

            activityLogService.save(
                    getSafeUserId(),
                    activityLogAction.actionType(),
                    httpRequest.getMethod(),
                    httpRequest.getRequestURI(),
                    RequestInfoUtil.getClientIp(httpRequest),
                    RequestInfoUtil.getUserAgent(httpRequest),
                    "500",
                    message
            );

            return e;
        }
    }

    private String getSafeUserId() {
        try {
            return SecurityUtil.getCurrentUserId();
        } catch (Exception e) {
            return "anonymous";
        }
    }

    private String buildMessage(
            ActivityLogAction activityLogAction,
            ProceedingJoinPoint joinPoint,
            Object result
    ) {
        StringBuilder builder = new StringBuilder();

        if (!activityLogAction.message().isBlank()) {
            builder.append(activityLogAction.message());
        }

        if (activityLogAction.includeArgs()) {
            Object[] args = joinPoint.getArgs();

            builder.append(" | args=");

            for (Object arg : args) {
                if (arg == null) {
                    builder.append("null");
                    continue;
                }

                String className = arg.getClass().getSimpleName();

                if (className.contains("MultipartFile")) {
                    builder.append("[MultipartFile]");
                } else if (arg instanceof jakarta.servlet.http.HttpServletRequest) {
                    builder.append("[HttpServletRequest]");
                } else if (arg instanceof jakarta.servlet.http.HttpServletResponse) {
                    builder.append("[HttpServletResponse]");
                } else {
                    builder.append(arg);
                }

                builder.append(", ");
            }
        }

        if (activityLogAction.includeResult() && result != null) {
            builder.append(" | result=").append(result);
        }

        return builder.toString();
    }
}
