package com.basic.backend.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMenuRoleRequest {
    private String canRead;
    private String canCreate;
    private String canUpdate;
    private String canDelete;
}
