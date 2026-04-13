package com.basic.backend.domain.controller;

import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.core.util.RequestInfoUtil;
import com.basic.backend.domain.dto.request.CreateMenuRequest;
import com.basic.backend.domain.dto.request.UpdateMenuRequest;
import com.basic.backend.domain.dto.response.AdminMyMenuTreeResponse;
import com.basic.backend.domain.dto.response.MenuResponse;
import com.basic.backend.domain.service.ActivityLogService;
import com.basic.backend.domain.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/menus")
public class MenuController {

    private final MenuService menuService;
    private final ActivityLogService activityLogService;

    public MenuController(MenuService menuService,
                          ActivityLogService activityLogService) {
        this.menuService = menuService;
        this.activityLogService = activityLogService;
    }

    @GetMapping
    public ApiResponse<List<MenuResponse>> findAll() {
        return ApiResponse.result(menuService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<MenuResponse> findById(@PathVariable Long id) {
        return ApiResponse.result(menuService.findById(id));
    }

    @PostMapping
    public ApiResponse<String> create(@RequestBody CreateMenuRequest request,
                                      HttpServletRequest httpRequest) {
        menuService.create(request);
        activityLogService.save(
                SecurityUtil.getCurrentUserId(),
                "MENU_CREATE",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                RequestInfoUtil.getClientIp(httpRequest),
                RequestInfoUtil.getUserAgent(httpRequest),
                "200",
                "menuNm" + request.getMenuNm() +
                        "menuPath" + request.getMenuPath() +
                        "parentId" + request.getParentId() +
                        "sortOrder" + request.getSortOrder() +
                        "icon" + request.getIcon() +
                        "visibleYn" + request.getVisibleYn() +
                        "status" + request.getStatus()
        );
        return ApiResponse.result("메뉴 등록 성공");
    }

    @PutMapping("/{id}")
    public ApiResponse<String> update(@PathVariable Long id,
                                      @RequestBody UpdateMenuRequest request,
                                      HttpServletRequest httpRequest) {
        menuService.update(id, request);
        activityLogService.save(
                SecurityUtil.getCurrentUserId(),
                "MENU_UPDATE",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                RequestInfoUtil.getClientIp(httpRequest),
                RequestInfoUtil.getUserAgent(httpRequest),
                "200",
                "id" + id +
                        "menuNm" + request.getMenuNm() +
                        "menuPath" + request.getMenuPath() +
                        "parentId" + request.getParentId() +
                        "sortOrder" + request.getSortOrder() +
                        "icon" + request.getIcon() +
                        "visibleYn" + request.getVisibleYn() +
                        "status" + request.getStatus()
        );
        return ApiResponse.result("메뉴 수정 성공");
    }

    @GetMapping("/me")
    public ApiResponse<List<AdminMyMenuTreeResponse>> findMyMenus() {
        String roleCode = getCurrentRoleCode();
        return ApiResponse.result(menuService.findMenuTreeByRoleCode(roleCode));
    }

    private String getCurrentRoleCode() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getAuthorities() == null) {
            return null;
        }

        return authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority().replace("ROLE_", ""))
                .orElse(null);
    }
}