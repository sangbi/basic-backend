package com.basic.backend.domain.service;

import com.basic.backend.domain.dto.response.RoleResponse;
import com.basic.backend.domain.mapper.RoleMapper;
import org.springframework.stereotype.Service;

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
}