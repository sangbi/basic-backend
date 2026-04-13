package com.basic.backend.domain.entity.vo;

import lombok.Data;

@Data
public class MyMenuPermissionFlatRow {
    private String menuPath;
    private String apiPath;
    private String canRead;
    private String canCreate;
    private String canUpdate;
    private String canDelete;
}
