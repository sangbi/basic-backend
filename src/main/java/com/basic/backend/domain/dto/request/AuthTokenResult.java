package com.basic.backend.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthTokenResult {
    private String accessToken;
    private String refreshToken;
    private String sessionKey;
}
