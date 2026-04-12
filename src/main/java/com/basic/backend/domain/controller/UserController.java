package com.basic.backend.domain.controller;

import com.basic.backend.core.auth.dto.request.RegisterRequest;
import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.core.paging.PageRequest;
import com.basic.backend.core.paging.PageResponse;
import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.core.util.RequestInfoUtil;
import com.basic.backend.domain.dto.request.InfoUserCondition;
import com.basic.backend.domain.dto.request.SearchUserCondition;
import com.basic.backend.domain.dto.request.UserUpdateCondition;
import com.basic.backend.domain.dto.response.UserListResponse;
import com.basic.backend.domain.entity.UserEntity;
import com.basic.backend.domain.service.ActivityLogService;
import com.basic.backend.domain.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ActivityLogService activityLogService;

    public UserController(UserService userService,
                          ActivityLogService activityLogService) {
        this.userService = userService;
        this.activityLogService = activityLogService;
    }

    @PostMapping("/search")
    public ApiResponse<PageResponse<UserListResponse>> search(
            @RequestBody PageRequest<SearchUserCondition> request
    ) {
        return ApiResponse.result(userService.search(request));
    }

    @PostMapping("/info")
    public ApiResponse<UserEntity> info(
            @RequestBody InfoUserCondition request
            ) {
        return ApiResponse.result(userService.info(request.getUserId()));
    }

    @PostMapping("/update")
    public ApiResponse<String> update(@RequestBody @Valid UserUpdateCondition request, HttpServletRequest httpRequest) {
        userService.update(request);

        activityLogService.save(
                SecurityUtil.getCurrentUserId(),
                "USER_UPDATE",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                RequestInfoUtil.getClientIp(httpRequest),
                RequestInfoUtil.getUserAgent(httpRequest),
                "200",
                "userId=" + request.getUserId()+",role="+request.getRole()
        );
        return ApiResponse.result("회원수정 성공");
    }

    @PostMapping("/delete")
    public ApiResponse<String> delete(@RequestBody InfoUserCondition request,HttpServletRequest httpRequest) {
        userService.delete(request.getUserId());

        activityLogService.save(
                SecurityUtil.getCurrentUserId(),
                "USER_DELETE",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                RequestInfoUtil.getClientIp(httpRequest),
                RequestInfoUtil.getUserAgent(httpRequest),
                "200",
                "userId=" + request.getUserId()
        );
        return ApiResponse.result("회원삭제 성공");
    }
}