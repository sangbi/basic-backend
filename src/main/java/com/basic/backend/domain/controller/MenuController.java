package com.basic.backend.domain.controller;

import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.domain.dto.request.CreateMenuRequest;
import com.basic.backend.domain.dto.request.UpdateMenuRequest;
import com.basic.backend.domain.dto.response.AdminMyMenuTreeResponse;
import com.basic.backend.domain.dto.response.MenuResponse;
import com.basic.backend.domain.service.MenuService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ApiResponse<List<MenuResponse>> findAll(
            @RequestParam(required = false) String menuSetCd
    ) {
        return ApiResponse.result(menuService.findAll(menuSetCd));
    }

    @GetMapping("/{id}")
    public ApiResponse<MenuResponse> findById(@PathVariable Long id) {
        return ApiResponse.result(menuService.findById(id));
    }

    @PostMapping
    public ApiResponse<String> create(@RequestBody CreateMenuRequest request) {
        menuService.create(request);

        return ApiResponse.result("메뉴 등록 성공");
    }

    @PutMapping("/{id}")
    public ApiResponse<String> update(@PathVariable Long id,
                                      @RequestBody UpdateMenuRequest request
    ) {
        menuService.update(id, request);

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