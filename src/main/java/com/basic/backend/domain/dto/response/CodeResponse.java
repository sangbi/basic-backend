package com.basic.backend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CodeResponse {
    private Long id;
    private Long groupId;
    private String groupCode;
    private String code;
    private String codeNm;
    private String description;
    private Integer sortOrder;
    private String status;
    private String extraValue;
}