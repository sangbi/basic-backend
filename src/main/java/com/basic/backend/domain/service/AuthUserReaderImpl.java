package com.basic.backend.domain.service;

import com.basic.backend.core.audit.AuditUtil;
import com.basic.backend.core.auth.model.AuthUser;
import com.basic.backend.core.auth.service.AuthUserReader;
import com.basic.backend.domain.entity.UserEntity;
import com.basic.backend.domain.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthUserReaderImpl implements AuthUserReader {

    private final UserMapper userMapper;

    public AuthUserReaderImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public AuthUser findByUserId(String userId) {
        UserEntity userEntity = userMapper.findByUserId(userId);

        if (userEntity == null) {
            return null;
        }

        return AuthUser.builder()
                .id(userEntity.getId())
                .userId(userEntity.getUserId())
                .password(userEntity.getPassword())
                .roleId(userEntity.getRoleId())
                .roleCode(userEntity.getRoleCode())
                .userNm(userEntity.getUserNm())
                .status(userEntity.getStatus())
                .build();
    }

    @Override
    @Transactional
    public void save(AuthUser authUser) {
        String currentUser = AuditUtil.getCurrentUser();
        UserEntity userEntity = UserEntity.builder()
                .userId(authUser.getUserId())
                .password(authUser.getPassword())
                .roleId(authUser.getRoleId())
                .createdBy(currentUser)
                .updatedBy(currentUser)
                .build();

        userMapper.insert(userEntity);
    }
}