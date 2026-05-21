package com.basic.backend.domain.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebMenuFlatRow {
    private Long id;
    private String menuNm;
    private String menuPath;
    private Long parentId;
    private Integer sortOrder;
}
