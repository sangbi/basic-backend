package com.basic.backend.domain.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateCondition {
    private Long roleId;
    private String userId;
}
