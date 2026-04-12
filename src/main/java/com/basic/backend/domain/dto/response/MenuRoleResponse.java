package com.basic.backend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuRoleResponse {
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
