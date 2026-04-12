package com.basic.backend.domain.controller;

import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.domain.dto.response.UserSessionResponse;
import com.basic.backend.domain.service.UserSessionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/sessions")
public class UserSessionController {

    private final UserSessionService userSessionService;

    public UserSessionController(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    @GetMapping("/active")
    public ApiResponse<List<UserSessionResponse>> findActiveSessions() {
        return ApiResponse.result(userSessionService.findActiveSessions());
    }
}
