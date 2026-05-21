package com.basic.backend.domain.controller;

import com.basic.backend.core.logging.ActivityLogAction;
import com.basic.backend.core.paging.PageRequest;
import com.basic.backend.core.paging.PageResponse;
import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.domain.dto.request.SearchUserCondition;
import com.basic.backend.domain.dto.request.UserUpdateCondition;
import com.basic.backend.domain.dto.response.UserListResponse;
import com.basic.backend.domain.entity.UserEntity;
import com.basic.backend.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public ApiResponse<PageResponse<UserListResponse>> search(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) String userId
    ) {
        PageRequest<SearchUserCondition> request = new PageRequest<SearchUserCondition>();
        SearchUserCondition params = new SearchUserCondition();
        params.setUserId(userId);
        request.setCondition(params);
        request.setPage(page);
        request.setSize(size);
        return ApiResponse.result(userService.search(request));
    }

    @GetMapping("/info/{userId}")
    public ApiResponse<UserEntity> info(@PathVariable String userId) {
        return ApiResponse.result(userService.info(userId));
    }

    @PutMapping("/update")
    public ApiResponse<String> update(@RequestBody @Valid UserUpdateCondition request) {
        userService.update(request);

        return ApiResponse.result("회원수정 성공");
    }

    @DeleteMapping("/delete/{userId}")
    public ApiResponse<String> delete(@PathVariable String userId) {
        userService.delete(userId);

        return ApiResponse.result("회원삭제 성공");
    }
}