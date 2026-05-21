package com.basic.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuEntity {
    private Long id;
    private String menuNm;
    private String menuPath;
    private String apiPath;
    private Long parentId;
    private Integer sortOrder;
    private String icon;
    private String visibleYn;
    private String status;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private Long menuSetId;
    private String menuSetCd;
    private String menuSetNm;
}
