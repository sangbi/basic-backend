package com.basic.backend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminMenuResponse {
    private Long id;
    private String menuNm;
    private String menuPath;
    private Long parentId;
    private Integer sortOrder;
    private String icon;
}
