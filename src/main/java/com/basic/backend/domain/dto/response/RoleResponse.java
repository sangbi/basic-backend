package com.basic.backend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoleResponse {
    private Long id;
    private String roleCode;
    private String roleNm;
    private String description;
    private String status;
}
