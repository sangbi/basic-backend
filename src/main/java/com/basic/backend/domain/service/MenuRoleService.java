package com.basic.backend.domain.service;

import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.domain.dto.request.UpdateMenuRoleRequest;
import com.basic.backend.domain.dto.response.MenuRoleResponse;
import com.basic.backend.domain.dto.response.MyMenuPermissionResponse;
import com.basic.backend.domain.entity.MenuRoleEntity;
import com.basic.backend.domain.entity.vo.MenuRoleFlatRow;
import com.basic.backend.domain.mapper.MenuRoleMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuRoleService {

    private final MenuRoleMapper menuRoleMapper;

    public MenuRoleService(MenuRoleMapper menuRoleMapper) {
        this.menuRoleMapper = menuRoleMapper;
    }

    public List<MenuRoleResponse> findAll() {
        return menuRoleMapper.findAllWithRoleAndMenu().stream()
                .map(this::toResponse)
                .toList();
    }

    public void updatePermissions(Long id, UpdateMenuRoleRequest request) {
        MenuRoleEntity entity = new MenuRoleEntity();
        entity.setId(id);
        entity.setCanRead(request.getCanRead());
        entity.setCanCreate(request.getCanCreate());
        entity.setCanUpdate(request.getCanUpdate());
        entity.setCanDelete(request.getCanDelete());
        entity.setUpdatedBy(SecurityUtil.getCurrentUserId());

        menuRoleMapper.updatePermissions(entity);
    }

    public List<MyMenuPermissionResponse> findMyPermissions() {
        String roleCode = getCurrentRoleCode();

        return menuRoleMapper.findMyPermissionsByRoleCode(roleCode).stream()
                .map(row -> MyMenuPermissionResponse.builder()
                        .menuPath(row.getMenuPath())
                        .apiPath(row.getApiPath())
                        .canRead(row.getCanRead())
                        .canCreate(row.getCanCreate())
                        .canUpdate(row.getCanUpdate())
                        .canDelete(row.getCanDelete())
                        .build())
                .toList();
    }

    private MenuRoleResponse toResponse(MenuRoleFlatRow row) {
        return MenuRoleResponse.builder()
                .id(row.getId())
                .menuId(row.getMenuId())
                .menuNm(row.getMenuNm())
                .roleId(row.getRoleId())
                .roleCode(row.getRoleCode())
                .canRead(row.getCanRead())
                .canCreate(row.getCanCreate())
                .canUpdate(row.getCanUpdate())
                .canDelete(row.getCanDelete())
                .build();
    }

    private String getCurrentRoleCode() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getAuthorities() == null) {
            return null;
        }

        return authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority().replace("ROLE_",""))
                .orElse(null);
    }
}