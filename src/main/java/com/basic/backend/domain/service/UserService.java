package com.basic.backend.domain.service;

import com.basic.backend.core.audit.AuditUtil;
import com.basic.backend.core.auth.model.AuthUser;
import com.basic.backend.core.logging.ActivityLogAction;
import com.basic.backend.core.paging.PageRequest;
import com.basic.backend.core.paging.PageResponse;
import com.basic.backend.core.paging.PageResult;
import com.basic.backend.core.security.jwt.JwtTokenProvider;
import com.basic.backend.domain.dto.request.*;
import com.basic.backend.domain.dto.response.LoginResponse;
import com.basic.backend.domain.dto.response.UserListResponse;
import com.basic.backend.domain.entity.UserEntity;
import com.basic.backend.domain.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public PageResponse<UserListResponse> search(PageRequest<SearchUserCondition> request){
        SearchUserRequest mapperRequest = SearchUserRequest.builder()
                .offset(request.getOffset())
                .limit(request.getLimit())
                .userId(request.getCondition().getUserId())
                .build();

        List<UserEntity> users = userMapper.findAllPaged(mapperRequest);
        long totalCount = userMapper.countAll(mapperRequest);

        List<UserListResponse> items = users.stream()
                .map(user -> UserListResponse.builder()
                        .id(user.getId())
                        .userId(user.getUserId())
                        .roleId(user.getRoleId())
                        .roleCode(user.getRoleCode())
                        .status(user.getStatus())
                        .userNm(user.getUserNm())
                        .email(user.getEmail())
                        .lastLoginAt(user.getLastLoginAt())
                        .build())
                .toList();

        return PageResult.of(items, request, totalCount);
    }

    public UserEntity info(String userId) {
        return userMapper.findByUserId(userId);
    }

    @Transactional
    @ActivityLogAction(actionType = "USER_UPDATE",message = "사용자 수정")
    public void update(UserUpdateCondition condition) {
        String currentUser = AuditUtil.getCurrentUser();
        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
                .roleId(condition.getRoleId())
                .updatedBy(currentUser)
                .userId(condition.getUserId())
                .userNm(condition.getUserNm())
                .build();

        userMapper.update(userUpdateRequest);
    }

    @Transactional
    @ActivityLogAction(actionType = "USER_DELETE",message = "사용자 삭제")
    public void delete(String userId) {
        userMapper.delete(userId);
    }
}