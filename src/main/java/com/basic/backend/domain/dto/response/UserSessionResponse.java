package com.basic.backend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserSessionResponse {
    private Long id;
    private String userId;
    private String sessionKey;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime loginAt;
    private LocalDateTime lastAccessAt;
    private LocalDateTime logoutAt;
    private String status;
    private LocalDateTime expiresAt;
}
