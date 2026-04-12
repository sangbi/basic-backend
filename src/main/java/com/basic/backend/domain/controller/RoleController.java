package com.basic.backend.domain.controller;

import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.domain.dto.response.RoleResponse;
import com.basic.backend.domain.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}