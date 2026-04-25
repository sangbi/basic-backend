package com.basic.backend.domain.service;

import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.domain.mapper.ResourceMapper;
import com.basic.backend.domain.dto.request.CreateResourceRequest;
import com.basic.backend.domain.dto.request.UpdateResourceRequest;
import com.basic.backend.domain.dto.response.ResourceResponse;
import com.basic.backend.domain.entity.ResourceEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResourceService {

    private final ResourceMapper resourceMapper;

    public ResourceService(ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
    }

    public List<ResourceResponse> findAll() {
        return resourceMapper.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ResourceResponse findById(Long id) {
        return toResponse(resourceMapper.findById(id));
    }

    @Transactional
    public Long create(CreateResourceRequest request) {
        ResourceEntity entity = new ResourceEntity();
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setStatus(request.getStatus());
        entity.setPinnedYn(request.getPinnedYn());
        entity.setCreatedBy(SecurityUtil.getCurrentUserId());
        entity.setUpdatedBy(SecurityUtil.getCurrentUserId());

        resourceMapper.insert(entity);
        return entity.getId();
    }

    public void update(Long id, UpdateResourceRequest request) {
        ResourceEntity entity = new ResourceEntity();
        entity.setId(id);
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setStatus(request.getStatus());
        entity.setPinnedYn(request.getPinnedYn());
        entity.setUpdatedBy(SecurityUtil.getCurrentUserId());

        resourceMapper.update(entity);
    }

    private ResourceResponse toResponse(ResourceEntity entity) {
        return ResourceResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .status(entity.getStatus())
                .pinnedYn(entity.getPinnedYn())
                .viewCnt(entity.getViewCnt())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }
}