package com.basic.backend.domain.controller;

import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.domain.dto.response.AdminDashboardSummaryResponse;
import com.basic.backend.domain.service.AdminDashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    public AdminDashboardController(AdminDashboardService adminDashboardService) {
        this.adminDashboardService = adminDashboardService;
    }

    @GetMapping("/summary")
    public ApiResponse<AdminDashboardSummaryResponse> getSummary() {
        return ApiResponse.result(adminDashboardService.getSummary());
    }
}
