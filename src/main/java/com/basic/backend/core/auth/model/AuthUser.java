package com.basic.backend.core.auth.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthUser {
    private Long id;
    private String userId;
    private String password;
    private Long roleId;
    private String roleCode;
    private String userNm;
    private String email;
    private String status;
}