package com.basic.backend.domain.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminMenuFlatRow {
    private Long id;
    private String menuNm;
    private String menuPath;
    private Long parentId;
    private Integer sortOrder;
    private String icon;
}
