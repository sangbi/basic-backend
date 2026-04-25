package com.basic.backend.domain.mapper;

import com.basic.backend.domain.entity.ResourceEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResourceMapper {
    List<ResourceEntity> findAll();
    ResourceEntity findById(Long id);
    Long insert(ResourceEntity entity);
    void update(ResourceEntity entity);
}
