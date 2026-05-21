package com.basic.backend.domain.controller;

import com.basic.backend.core.logging.ActivityLogAction;
import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.domain.dto.request.UpdateMenuRoleRequest;
import com.basic.backend.domain.dto.response.MenuRoleResponse;
import com.basic.backend.domain.dto.response.MyMenuPermissionResponse;
import com.basic.backend.domain.service.MenuRoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/menu-roles")
public class MenuRoleController {

    private final MenuRoleService menuRoleService;

    public MenuRoleController(MenuRoleService menuRoleService) {
        this.menuRoleService = menuRoleService;
    }

    @GetMapping
    public ApiResponse<List<MenuRoleResponse>> findAll() {
        return ApiResponse.result(menuRoleService.findAll());
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updatePermissions(
            @PathVariable Long id,
            @RequestBody UpdateMenuRoleRequest request
    ) {
        menuRoleService.updatePermissions(id, request);

        return ApiResponse.result("메뉴 권한 수정 성공");
    }

    @GetMapping("/me")
    public ApiResponse<List<MyMenuPermissionResponse>> findMyPermissions() {
        return ApiResponse.result(menuRoleService.findMyPermissions());
    }
}