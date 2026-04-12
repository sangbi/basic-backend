package com.basic.backend.core.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MeResponse {
    private String userId;
    private String role;
}