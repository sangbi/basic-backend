package com.basic.backend.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RefreshTokenEntity {
    private Long id;
    private String userId;
    private String refreshToken;
    private LocalDateTime expiresAt;
}