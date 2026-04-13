package com.basic.backend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AdminMyMenuTreeResponse {
    private Long id;
    private String menuNm;
    private String menuPath;
    private String icon;
    private Long parentId;
    private Integer sortOrder;
    private List<AdminMyMenuTreeResponse> children;
}