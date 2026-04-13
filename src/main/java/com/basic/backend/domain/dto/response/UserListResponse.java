package com.basic.backend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserListResponse {
    private Long id;
    private String userId;
    private Long roleId;
    private String roleCode;
    private String status;
    private String userNm;
    private String email;
    private LocalDateTime lastLoginAt;
}