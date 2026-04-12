package com.basic.backend.domain.service;

import com.basic.backend.domain.dto.response.MenuResponse;
import com.basic.backend.domain.mapper.MenuMapper;
import org.springframework.stereotype.Service;

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
                        .parentId(menu.getParentId())
                        .sortOrder(menu.getSortOrder())
                        .icon(menu.getIcon())
                        .visibleYn(menu.getVisibleYn())
                        .status(menu.getStatus())
                        .build())
                .toList();
    }
}