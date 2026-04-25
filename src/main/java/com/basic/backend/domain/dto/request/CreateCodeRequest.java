package com.basic.backend.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCodeRequest {
    private Long groupId;
    private String code;
    private String codeNm;
    private String description;
    private Integer sortOrder;
    private String status;
    private String extraValue;
}