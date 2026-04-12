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
public class MenuRoleEntity {
    private Long id;
    private Long menuId;
    private Long roleId;
    private String canRead;
    private String canCreate;
    private String canUpdate;
    private String canDelete;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
