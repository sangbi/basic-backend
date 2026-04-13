package com.basic.backend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyMenuPermissionResponse {
    private String menuPath;
    private String apiPath;
    private String canRead;
    private String canCreate;
    private String canUpdate;
    private String canDelete;
}
