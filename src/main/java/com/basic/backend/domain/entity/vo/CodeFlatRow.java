package com.basic.backend.domain.entity.vo;

import lombok.Data;

@Data
public class CodeFlatRow {
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