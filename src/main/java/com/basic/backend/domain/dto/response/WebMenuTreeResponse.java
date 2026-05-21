package com.basic.backend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class WebMenuTreeResponse {
    private Long id;
    private String menuNm;
    private String menuPath;
    private Long parentId;
    private Integer sortOrder;
    private List<WebMenuTreeResponse> children;
}