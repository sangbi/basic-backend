package com.basic.backend.domain.entity.vo;

import lombok.Data;

@Data
public class MenuRoleFlatRow {
    private Long id;
    private Long menuId;
    private String menuNm;
    private Long roleId;
    private String roleCode;
    private String canRead;
    private String canCreate;
    private String canUpdate;
    private String canDelete;
}
