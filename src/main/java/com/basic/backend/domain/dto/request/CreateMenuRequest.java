package com.basic.backend.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateMenuRequest {
    private String menuNm;
    private String menuPath;
    private String apiPath;
    private Long parentId;
    private Integer sortOrder;
    private String icon;
    private String visibleYn;
    private String status;
}
