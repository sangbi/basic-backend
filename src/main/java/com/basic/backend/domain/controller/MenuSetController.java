package com.basic.backend.domain.controller;

import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.domain.dto.request.CreateMenuRequest;
import com.basic.backend.domain.dto.request.UpdateMenuRequest;
import com.basic.backend.domain.dto.response.AdminMyMenuTreeResponse;
import com.basic.backend.domain.dto.response.MenuResponse;
import com.basic.backend.domain.dto.response.MenuSetResponse;
import com.basic.backend.domain.service.MenuService;
import com.basic.backend.domain.service.MenuSetService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/menu-sets")
public class MenuSetController {
    private final MenuSetService menuSetService;

    public MenuSetController(MenuSetService menuSetService) {
        this.menuSetService = menuSetService;
    }

    @GetMapping
    public ApiResponse<List<MenuSetResponse>> findAll() {
        return ApiResponse.result(menuSetService.findAll());
    }
}