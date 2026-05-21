package com.basic.backend.domain.controller;

import com.basic.backend.core.logging.ActivityLogAction;
import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.domain.dto.request.CreateRoleRequest;
import com.basic.backend.domain.dto.request.UpdateRoleRequest;
import com.basic.backend.domain.dto.response.RoleResponse;
import com.basic.backend.domain.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> findAll() {
        return ApiResponse.result(roleService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<RoleResponse> findById(@PathVariable Long id, HttpServletRequest httpRequest) {
        return ApiResponse.result(roleService.findById(id));
    }

    @PostMapping
    public ApiResponse<String> create(@RequestBody CreateRoleRequest request,HttpServletRequest httpRequest) {
        roleService.create(request);

        return ApiResponse.result("역할 등록 성공");
    }

    @PutMapping("/{id}")
    public ApiResponse<String> update(
            @PathVariable Long id,
            @RequestBody UpdateRoleRequest request
            ) {
        roleService.update(id, request);

        return ApiResponse.result("역할 수정 성공");
    }
}