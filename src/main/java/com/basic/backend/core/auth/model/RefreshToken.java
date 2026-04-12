package com.basic.backend.core.auth.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RefreshToken {
    private Long id;
    private String userId;
    private String refreshToken;
    private LocalDateTime expiresAt;
}