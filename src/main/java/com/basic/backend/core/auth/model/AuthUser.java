package com.basic.backend.core.auth.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthUser {
    private Long id;
    private String userId;
    private String password;
    private String role;
}