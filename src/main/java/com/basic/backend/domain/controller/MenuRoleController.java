package com.basic.backend.domain.controller;

import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.core.util.RequestInfoUtil;
import com.basic.backend.domain.dto.request.UpdateMenuRoleRequest;
import com.basic.backend.domain.dto.response.MenuRoleResponse;
import com.basic.backend.domain.service.ActivityLogService;
import com.basic.backend.domain.service.MenuRoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/menu-roles")
public class MenuRoleController {

    private final MenuRoleService menuRoleService;
    private final ActivityLogService activityLogService;

    public MenuRoleController(MenuRoleService menuRoleService,
                              ActivityLogService activityLogService) {
        this.menuRoleService = menuRoleService;
        this.activityLogService = activityLogService;
    }

    @GetMapping
    public ApiResponse<List<MenuRoleResponse>> findAll() {
        return ApiResponse.result(menuRoleService.findAll());
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updatePermissions(
            @PathVariable Long id,
            @RequestBody UpdateMenuRoleRequest request,
            HttpServletRequest httpRequest
    ) {
        menuRoleService.updatePermissions(id, request);

        activityLogService.save(
                SecurityUtil.getCurrentUserId(),
                "MENU_ROLE_UPDATE",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                RequestInfoUtil.getClientIp(httpRequest),
                RequestInfoUtil.getUserAgent(httpRequest),
                "200",
                "Id=" + id+
                        ", canRead=" +request.getCanRead()+
                        ", canCreate=" +request.getCanCreate()+
                        ", canUpdate=" +request.getCanUpdate()+
                        ", canDelete=" +request.getCanDelete()
        );
        return ApiResponse.result("메뉴 권한 수정 성공");
    }
}