package com.basic.backend.domain.service;

import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.domain.dto.request.CreateMenuRequest;
import com.basic.backend.domain.dto.request.UpdateMenuRequest;
import com.basic.backend.domain.dto.response.AdminMenuResponse;
import com.basic.backend.domain.dto.response.MenuResponse;
import com.basic.backend.domain.entity.MenuEntity;
import com.basic.backend.domain.entity.vo.AdminMenuFlatRow;
import com.basic.backend.domain.mapper.MenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuService {

    private final MenuMapper menuMapper;

    public MenuService(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    public List<MenuResponse> findAll() {
        return menuMapper.findAll().stream()
                .map(menu -> MenuResponse.builder()
                        .id(menu.getId())
                        .menuNm(menu.getMenuNm())
                        .menuPath(menu.getMenuPath())
                        .apiPath(menu.getApiPath())
                        .parentId(menu.getParentId())
                        .sortOrder(menu.getSortOrder())
                        .icon(menu.getIcon())
                        .visibleYn(menu.getVisibleYn())
                        .status(menu.getStatus())
                        .build())
                .toList();
    }

    public List<AdminMenuResponse> findMenusByRoleCode(String roleCode) {
        return menuMapper.findMenusByRoleCode(roleCode).stream()
                .map(this::toAdminMyMenuResponse)
                .toList();
    }

    public MenuResponse findById(Long id) {
        return toResponse(menuMapper.findById(id));
    }

    @Transactional
    public void create(CreateMenuRequest request) {
        MenuEntity entity = MenuEntity.builder()
                .menuNm(request.getMenuNm())
                .menuPath(request.getMenuPath())
                .apiPath(request.getApiPath())
                .parentId(request.getParentId())
                .sortOrder(request.getSortOrder())
                .icon(request.getIcon())
                .visibleYn(request.getVisibleYn())
                .status(request.getStatus())
                .createdBy(SecurityUtil.getCurrentUserId())
                .updatedBy(SecurityUtil.getCurrentUserId())
                .build();

        menuMapper.insert(entity);
    }

    @Transactional
    public void update(Long id, UpdateMenuRequest request) {
        MenuEntity entity = MenuEntity.builder()
                .id(id)
                .menuNm(request.getMenuNm())
                .menuPath(request.getMenuPath())
                .apiPath(request.getApiPath())
                .parentId(request.getParentId())
                .sortOrder(request.getSortOrder())
                .icon(request.getIcon())
                .visibleYn(request.getVisibleYn())
                .status(request.getStatus())
                .updatedBy(SecurityUtil.getCurrentUserId())
                .build();

        menuMapper.update(entity);
    }

    private AdminMenuResponse toAdminMyMenuResponse(AdminMenuFlatRow row) {
        return AdminMenuResponse.builder()
                .id(row.getId())
                .menuNm(row.getMenuNm())
                .menuPath(row.getMenuPath())
                .parentId(row.getParentId())
                .sortOrder(row.getSortOrder())
                .icon(row.getIcon())
                .build();
    }

    private MenuResponse toResponse(MenuEntity menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .menuNm(menu.getMenuNm())
                .menuPath(menu.getMenuPath())
                .apiPath(menu.getApiPath())
                .parentId(menu.getParentId())
                .sortOrder(menu.getSortOrder())
                .icon(menu.getIcon())
                .visibleYn(menu.getVisibleYn())
                .status(menu.getStatus())
                .build();
    }
}