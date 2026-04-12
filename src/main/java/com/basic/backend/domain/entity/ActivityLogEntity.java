package com.basic.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLogEntity {
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