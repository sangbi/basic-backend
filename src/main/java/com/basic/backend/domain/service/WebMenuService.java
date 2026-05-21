package com.basic.backend.domain.service;

import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.core.logging.ActivityLogAction;
import com.basic.backend.domain.dto.request.CreateMenuRequest;
import com.basic.backend.domain.dto.request.UpdateMenuRequest;
import com.basic.backend.domain.dto.response.AdminMyMenuTreeResponse;
import com.basic.backend.domain.dto.response.MenuResponse;
import com.basic.backend.domain.dto.response.WebMenuTreeResponse;
import com.basic.backend.domain.entity.MenuEntity;
import com.basic.backend.domain.entity.vo.AdminMenuFlatRow;
import com.basic.backend.domain.entity.vo.WebMenuFlatRow;
import com.basic.backend.domain.mapper.MenuMapper;
import com.basic.backend.domain.mapper.WebMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WebMenuService {

    private final WebMenuMapper menuMapper;

    public WebMenuService(WebMenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    public List<WebMenuTreeResponse> findWebMenuTree() {
        List<WebMenuFlatRow> rows = menuMapper.findMenus();

        List<WebMenuTreeResponse> allMenus = rows.stream()
                .map(row -> WebMenuTreeResponse.builder()
                        .id(row.getId())
                        .menuNm(row.getMenuNm())
                        .menuPath(row.getMenuPath())
                        .parentId(row.getParentId())
                        .sortOrder(row.getSortOrder())
                        .children(new java.util.ArrayList<>())
                        .build())
                .toList();

        java.util.Map<Long, WebMenuTreeResponse> menuMap = allMenus.stream()
                .collect(java.util.stream.Collectors.toMap(
                        WebMenuTreeResponse::getId,
                        menu -> menu
                ));

        List<WebMenuTreeResponse> roots = new java.util.ArrayList<>();

        for (WebMenuTreeResponse menu : allMenus) {
            if (menu.getParentId() == null) {
                roots.add(menu);
                continue;
            }

            WebMenuTreeResponse parent = menuMap.get(menu.getParentId());
            if (parent != null) {
                parent.getChildren().add(menu);
            } else {
                roots.add(menu);
            }
        }

        roots.sort(java.util.Comparator.comparing(WebMenuTreeResponse::getSortOrder));
        roots.forEach(this::sortChildrenRecursively);

        return roots;
    }

    private void sortChildrenRecursively(WebMenuTreeResponse menu) {
        if (menu.getChildren() == null || menu.getChildren().isEmpty()) {
            return;
        }

        menu.getChildren().sort(java.util.Comparator.comparing(WebMenuTreeResponse::getSortOrder));
        menu.getChildren().forEach(this::sortChildrenRecursively);
    }
}