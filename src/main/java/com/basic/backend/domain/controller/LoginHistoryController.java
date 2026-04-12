package com.basic.backend.domain.controller;

import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.domain.dto.response.LoginHistoryReponse;
import com.basic.backend.domain.service.LoginHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/login-histories")
public class LoginHistoryController {

    private final LoginHistoryService loginHistoryService;

    public LoginHistoryController(LoginHistoryService loginHistoryService) {
        this.loginHistoryService = loginHistoryService;
    }

    @GetMapping
    public ApiResponse<List<LoginHistoryReponse>> findAll() {
        return ApiResponse.result(loginHistoryService.findAll());
    }
}
