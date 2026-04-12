package com.basic.backend.domain.controller;

import com.basic.backend.core.auth.dto.request.RegisterRequest;
import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.core.paging.PageRequest;
import com.basic.backend.core.paging.PageResponse;
import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.core.util.RequestInfoUtil;
import com.basic.backend.domain.dto.request.InfoUserCondition;
import com.basic.backend.domain.dto.request.SearchUserCondition;
import com.basic.backend.domain.dto.request.SearchUserPageRequest;
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

    @GetMapping("/search")
    public ApiResponse<PageResponse<UserListResponse>> search(
            @ModelAttribute SearchUserPageRequest request
    ) {
        return ApiResponse.result(userService.search(request));
    }

    @GetMapping("/info/{userId}")
    public ApiResponse<UserEntity> info(@PathVariable String userId) {
        return ApiResponse.result(userService.info(userId));
    }

    @PutMapping("/update")
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
                "userId=" + request.getUserId()+",roleId="+request.getRoleId()
        );
        return ApiResponse.result("회원수정 성공");
    }

    @DeleteMapping("/delete/{userId}")
    public ApiResponse<String> delete(@PathVariable String userId,HttpServletRequest httpRequest) {
        userService.delete(userId);

        activityLogService.save(
                SecurityUtil.getCurrentUserId(),
                "USER_DELETE",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                RequestInfoUtil.getClientIp(httpRequest),
                RequestInfoUtil.getUserAgent(httpRequest),
                "200",
                "userId=" + userId
        );
        return ApiResponse.result("회원삭제 성공");
    }
}