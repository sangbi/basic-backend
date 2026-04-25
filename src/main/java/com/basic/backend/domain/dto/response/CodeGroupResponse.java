package com.basic.backend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CodeGroupResponse {
    private Long id;
    private String groupCode;
    private String groupNm;
    private String description;
    private String status;
}