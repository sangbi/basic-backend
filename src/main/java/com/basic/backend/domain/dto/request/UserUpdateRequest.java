package com.basic.backend.domain.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateRequest {
    private String updatedBy;
    private String userId;
    private String userNm;
    private Long roleId;
}
