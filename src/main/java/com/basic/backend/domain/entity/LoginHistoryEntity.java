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
public class LoginHistoryEntity {
    private Long id;
    private String userId;
    private String loginResult;
    private LocalDateTime loginAt;
    private LocalDateTime logoutAt;
    private String ipAddress;
    private String userAgent;
    private String failReason;
    private String sessionKey;
    private LocalDateTime createdAt;
}