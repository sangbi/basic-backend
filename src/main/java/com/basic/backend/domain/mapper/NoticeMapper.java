package com.basic.backend.domain.mapper;

import com.basic.backend.domain.dto.request.SearchNoticeRequest;
import com.basic.backend.domain.entity.NoticeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    List<NoticeEntity> findAllPaged(SearchNoticeRequest pageRequest);
    long countAll(SearchNoticeRequest pagteRequest);
    NoticeEntity findById(Long id);
    Long insert(NoticeEntity entity);
    void update(NoticeEntity entity);
    void deleteById(NoticeEntity entity);
    NoticeEntity findActiveById(Long id);
    void increaseViewCount(Long id);
}
