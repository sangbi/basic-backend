package com.basic.backend.domain.controller;

import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.domain.dto.response.MenuResponse;
import com.basic.backend.domain.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ApiResponse<List<MenuResponse>> findAll() {
        return ApiResponse.result(menuService.findAll());
    }
}