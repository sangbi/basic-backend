package com.basic.backend.domain.mapper;

import com.basic.backend.domain.dto.request.SearchResourceRequest;
import com.basic.backend.domain.entity.NoticeEntity;
import com.basic.backend.domain.entity.ResourceEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResourceMapper {
    List<ResourceEntity> findAllPaged(SearchResourceRequest pageRequest);
    long countAll(SearchResourceRequest pagteRequest);
    ResourceEntity findById(Long id);
    Long insert(ResourceEntity entity);
    void update(ResourceEntity entity);
    ResourceEntity findActiveById(Long id);
    void increaseViewCount(Long id);
}
