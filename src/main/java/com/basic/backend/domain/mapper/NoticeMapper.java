package com.basic.backend.domain.mapper;

import com.basic.backend.domain.entity.NoticeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    List<NoticeEntity> findAll();
    NoticeEntity findById(Long id);
    Long insert(NoticeEntity entity);
    void update(NoticeEntity entity);
}
