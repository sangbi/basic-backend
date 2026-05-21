package com.basic.backend.domain.controller;

import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.domain.dto.request.CreateMenuRequest;
import com.basic.backend.domain.dto.request.UpdateMenuRequest;
import com.basic.backend.domain.dto.response.AdminMyMenuTreeResponse;
import com.basic.backend.domain.dto.response.MenuResponse;
import com.basic.backend.domain.dto.response.WebMenuTreeResponse;
import com.basic.backend.domain.service.MenuService;
import com.basic.backend.domain.service.WebMenuService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web/menus")
public class WebMenuController {

    private final WebMenuService menuService;

    public WebMenuController(WebMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ApiResponse<List<WebMenuTreeResponse>> findWebMenus() {
        return ApiResponse.result(menuService.findWebMenuTree());
    }
}