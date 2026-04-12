package com.basic.backend.domain.mapper;

import com.basic.backend.domain.entity.ActivityLogEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityLogMapper {
    void insert(ActivityLogEntity entity);
    List<ActivityLogEntity> findAll();
    long countToday();
}