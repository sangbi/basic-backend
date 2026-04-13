package com.basic.backend.domain.service;

import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.domain.dto.request.CreateMenuRequest;
import com.basic.backend.domain.dto.request.UpdateMenuRequest;
import com.basic.backend.domain.dto.response.AdminMyMenuTreeResponse;
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

    public List<AdminMyMenuTreeResponse> findMenuTreeByRoleCode(String roleCode) {
        List<AdminMenuFlatRow> rows = menuMapper.findMenusByRoleCode(roleCode);

        List<AdminMyMenuTreeResponse> allMenus = rows.stream()
                .map(row -> AdminMyMenuTreeResponse.builder()
                        .id(row.getId())
                        .menuNm(row.getMenuNm())
                        .menuPath(row.getMenuPath())
                        .icon(row.getIcon())
                        .parentId(row.getParentId())
                        .sortOrder(row.getSortOrder())
                        .children(new java.util.ArrayList<>())
                        .build())
                .toList();

        java.util.Map<Long, AdminMyMenuTreeResponse> menuMap = allMenus.stream()
                .collect(java.util.stream.Collectors.toMap(
                        AdminMyMenuTreeResponse::getId,
                        menu -> menu
                ));

        java.util.List<AdminMyMenuTreeResponse> roots = new java.util.ArrayList<>();

        for (AdminMyMenuTreeResponse menu : allMenus) {
            if (menu.getParentId() == null) {
                roots.add(menu);
                continue;
            }

            AdminMyMenuTreeResponse parent = menuMap.get(menu.getParentId());
            if (parent != null) {
                parent.getChildren().add(menu);
            } else {
                roots.add(menu);
            }
        }

        roots.sort(java.util.Comparator.comparing(AdminMyMenuTreeResponse::getSortOrder));
        roots.forEach(this::sortChildrenRecursively);

        return roots;
    }

    private void sortChildrenRecursively(AdminMyMenuTreeResponse menu) {
        if (menu.getChildren() == null || menu.getChildren().isEmpty()) {
            return;
        }

        menu.getChildren().sort(java.util.Comparator.comparing(AdminMyMenuTreeResponse::getSortOrder));
        menu.getChildren().forEach(this::sortChildrenRecursively);
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