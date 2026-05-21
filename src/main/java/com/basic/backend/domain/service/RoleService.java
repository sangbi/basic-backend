package com.basic.backend.domain.service;

import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.core.logging.ActivityLogAction;
import com.basic.backend.domain.dto.request.CreateRoleRequest;
import com.basic.backend.domain.dto.request.UpdateRoleRequest;
import com.basic.backend.domain.dto.response.RoleResponse;
import com.basic.backend.domain.entity.RoleEntity;
import com.basic.backend.domain.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {

    private final RoleMapper roleMapper;

    public RoleService(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public List<RoleResponse> findAll() {
        return roleMapper.findAll().stream()
                .map(role -> RoleResponse.builder()
                        .id(role.getId())
                        .roleCode(role.getRoleCode())
                        .roleNm(role.getRoleNm())
                        .description(role.getDescription())
                        .status(role.getStatus())
                        .build())
                .toList();
    }

    public RoleResponse findById(Long id) {
        return toResponse(roleMapper.findById(id));
    }

    @Transactional
    @ActivityLogAction(actionType = "ROLE_CREATE",message = "권한 등록")
    public void create(CreateRoleRequest request) {
        RoleEntity entity = RoleEntity.builder()
                .roleCode(request.getRoleCode())
                .roleNm(request.getRoleNm())
                .description(request.getDescription())
                .status(request.getStatus())
                .createdBy(SecurityUtil.getCurrentUserId())
                .updatedBy(SecurityUtil.getCurrentUserId())
                .build();

        roleMapper.insert(entity);
    }

    @Transactional
    @ActivityLogAction(actionType = "ROLE_UPDATE",message = "권한 수정")
    public void update(Long id, UpdateRoleRequest request) {
        RoleEntity entity = RoleEntity.builder()
                .id(id)
                .roleNm(request.getRoleNm())
                .description(request.getDescription())
                .status(request.getStatus())
                .updatedBy(SecurityUtil.getCurrentUserId())
                .build();

        roleMapper.update(entity);
    }

    private RoleResponse toResponse(RoleEntity role) {
        return RoleResponse.builder()
                .id(role.getId())
                .roleCode(role.getRoleCode())
                .roleNm(role.getRoleNm())
                .description(role.getDescription())
                .status(role.getStatus())
                .build();
    }
}