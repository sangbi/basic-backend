package com.basic.backend.domain.service;

import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.domain.dto.request.CreateCodeGroupRequest;
import com.basic.backend.domain.dto.request.CreateCodeRequest;
import com.basic.backend.domain.dto.request.UpdateCodeGroupRequest;
import com.basic.backend.domain.dto.request.UpdateCodeRequest;
import com.basic.backend.domain.dto.response.CodeGroupResponse;
import com.basic.backend.domain.dto.response.CodeResponse;
import com.basic.backend.domain.entity.CodeEntity;
import com.basic.backend.domain.entity.CodeGroupEntity;
import com.basic.backend.domain.mapper.CodeMapper;
import com.basic.backend.domain.entity.vo.CodeFlatRow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CodeService {

    private final CodeMapper codeMapper;

    public CodeService(CodeMapper codeMapper) {
        this.codeMapper = codeMapper;
    }

    public List<CodeGroupResponse> findAllGroups() {
        return codeMapper.findAllGroups().stream()
                .map(group -> CodeGroupResponse.builder()
                        .id(group.getId())
                        .groupCode(group.getGroupCode())
                        .groupNm(group.getGroupNm())
                        .description(group.getDescription())
                        .status(group.getStatus())
                        .build())
                .toList();
    }

    @Transactional
    public void createGroup(CreateCodeGroupRequest request) {
        String userId = SecurityUtil.getCurrentUserId();

        CodeGroupEntity entity = CodeGroupEntity.builder()
                .groupCode(request.getGroupCode())
                .groupNm(request.getGroupNm())
                .description(request.getDescription())
                .status(request.getStatus())
                .createdBy(userId)
                .updatedBy(userId)
                .build();

        codeMapper.insertGroup(entity);
    }

    @Transactional
    public void updateGroup(Long id, UpdateCodeGroupRequest request) {
        String userId = SecurityUtil.getCurrentUserId();

        CodeGroupEntity entity = CodeGroupEntity.builder()
                .id(id)
                .groupNm(request.getGroupNm())
                .description(request.getDescription())
                .status(request.getStatus())
                .updatedBy(userId)
                .build();

        codeMapper.updateGroup(entity);
    }

    public List<CodeResponse> findAllCodes() {
        return codeMapper.findAllCodes().stream()
                .map(this::toCodeResponse)
                .toList();
    }

    public List<CodeResponse> findCodesByGroupCode(String groupCode) {
        return codeMapper.findCodesByGroupCode(groupCode).stream()
                .map(this::toCodeResponse)
                .toList();
    }

    @Transactional
    public void createCode(CreateCodeRequest request) {
        CodeEntity entity = CodeEntity.builder()
                .groupId(request.getGroupId())
                .code(request.getCode())
                .codeNm(request.getCodeNm())
                .description(request.getDescription())
                .sortOrder(request.getSortOrder())
                .status(request.getStatus())
                .extraValue(request.getExtraValue())
                .createdBy(SecurityUtil.getCurrentUserId())
                .updatedBy(SecurityUtil.getCurrentUserId())
                .build();

        codeMapper.insertCode(entity);
    }

    @Transactional
    public void updateCode(Long id, UpdateCodeRequest request) {
        CodeEntity entity = CodeEntity.builder()
                .id(id)
                .codeNm(request.getCodeNm())
                .description(request.getDescription())
                .sortOrder(request.getSortOrder())
                .status(request.getStatus())
                .extraValue(request.getExtraValue())
                .updatedBy(SecurityUtil.getCurrentUserId())
                .build();

        codeMapper.updateCode(entity);
    }

    private CodeResponse toCodeResponse(CodeFlatRow row) {
        return CodeResponse.builder()
                .id(row.getId())
                .groupId(row.getGroupId())
                .groupCode(row.getGroupCode())
                .code(row.getCode())
                .codeNm(row.getCodeNm())
                .description(row.getDescription())
                .sortOrder(row.getSortOrder())
                .status(row.getStatus())
                .extraValue(row.getExtraValue())
                .build();
    }
}