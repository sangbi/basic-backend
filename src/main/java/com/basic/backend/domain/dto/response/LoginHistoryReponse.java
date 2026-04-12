package com.basic.backend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LoginHistoryReponse {
    private Long id;
    private String userId;
    private String loginResult;
    private LocalDateTime loginAt;
    private LocalDateTime logoutAt;
    private String ipAddress;
    private String userAgent;
    private String failReason;
    private String sessionKey;
}
