package com.basic.backend.domain.service;

import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.core.exception.BasicException;
import com.basic.backend.core.logging.ActivityLogAction;
import com.basic.backend.core.paging.PageRequest;
import com.basic.backend.core.paging.PageResponse;
import com.basic.backend.core.paging.PageResult;
import com.basic.backend.core.response.ErrorCode;
import com.basic.backend.domain.dto.request.CreateResourceRequest;
import com.basic.backend.domain.dto.request.SearchResourceCondition;
import com.basic.backend.domain.dto.request.SearchResourceRequest;
import com.basic.backend.domain.dto.request.UpdateResourceRequest;
import com.basic.backend.domain.dto.response.ResourceResponse;
import com.basic.backend.domain.entity.ResourceEntity;
import com.basic.backend.domain.mapper.ResourceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResourceService {

    private final ResourceMapper resourceMapper;

    public ResourceService(ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
    }

    public PageResponse<ResourceResponse> search(PageRequest<SearchResourceCondition> request) {
        SearchResourceRequest mapperRequest = SearchResourceRequest.builder()
                .offset(request.getOffset())
                .limit(request.getLimit())
                .title(request.getCondition().getTitle())
                .build();

        List<ResourceEntity> list = resourceMapper.findAllPaged(mapperRequest);
        long totlaCount = resourceMapper.countAll(mapperRequest);

        List<ResourceResponse> items = list.stream()
                .map(resource -> ResourceResponse.builder()
                        .id(resource.getId())
                        .title(resource.getTitle())
                        .content(resource.getContent())
                        .status(resource.getStatus())
                        .pinnedYn(resource.getPinnedYn())
                        .viewCnt(resource.getViewCnt())
                        .createdAt(resource.getCreatedAt())
                        .createdBy(resource.getCreatedBy())
                        .updatedAt(resource.getUpdatedAt())
                        .updatedBy(resource.getUpdatedBy())
                        .build()
                ).toList();
        return PageResult.of(items, request, totlaCount);
    }

    public ResourceResponse findById(Long id) {
        return toResponse(resourceMapper.findById(id));
    }

    @Transactional
    public ResourceResponse findActiveById(Long id) {
        resourceMapper.increaseViewCount(id);
        ResourceEntity entity = resourceMapper.findActiveById(id);

        if (entity == null) {
            throw new BasicException(ErrorCode.DATA_NOT_FOUND);
        }

        return toResponse(entity);
    }

    @Transactional
    @ActivityLogAction(actionType = "RESOURCE_CREATE",message = "자료실 등록")
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

    @Transactional
    @ActivityLogAction(actionType = "RESOURCE_UPDATE",message = "자료실 수정")
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