package com.basic.backend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ActivityLogResponse {
    private Long id;
    private String userId;
    private String actionType;
    private String httpMethod;
    private String requestUri;
    private String ipAddress;
    private String userAgent;
    private String responseCode;
    private String requestBodySummary;
    private LocalDateTime createdAt;
}
