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
public class UserSessionEntity {
    private Long id;
    private String userId;
    private String sessionKey;
    private String refreshToken;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime loginAt;
    private LocalDateTime lastAccessAt;
    private LocalDateTime logoutAt;
    private String status;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
}