package com.basic.backend.domain.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateCondition {
    private String role;
    private String userId;
}
