package com.basic.backend.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateRoleRequest {
    private String roleNm;
    private String description;
    private String status;
}
