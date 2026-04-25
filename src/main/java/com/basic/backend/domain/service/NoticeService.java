package com.basic.backend.domain.service;

import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.domain.dto.request.CreateNoticeRequest;
import com.basic.backend.domain.dto.request.UpdateNoticeRequest;
import com.basic.backend.domain.dto.response.NoticeResponse;
import com.basic.backend.domain.entity.NoticeEntity;
import com.basic.backend.domain.mapper.NoticeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoticeService {

    private final NoticeMapper noticeMapper;

    public NoticeService(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    public List<NoticeResponse> findAll() {
        return noticeMapper.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public NoticeResponse findById(Long id) {
        return toResponse(noticeMapper.findById(id));
    }

    @Transactional
    public Long create(CreateNoticeRequest request) {
        NoticeEntity entity = NoticeEntity.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .noticeType(request.getNoticeType())
                .status(request.getStatus())
                .pinnedYn(request.getPinnedYn())
                .createdBy(SecurityUtil.getCurrentUserId())
                .updatedBy(SecurityUtil.getCurrentUserId())
                .build();

        noticeMapper.insert(entity);
        return entity.getId();
    }

    @Transactional
    public void update(Long id, UpdateNoticeRequest request) {
        NoticeEntity entity = NoticeEntity.builder()
                .id(id)
                .title(request.getTitle())
                .content(request.getContent())
                .noticeType(request.getNoticeType())
                .status(request.getStatus())
                .pinnedYn(request.getPinnedYn())
                .updatedBy(SecurityUtil.getCurrentUserId())
                .build();

        noticeMapper.update(entity);
    }

    private NoticeResponse toResponse(NoticeEntity entity) {
        return NoticeResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .noticeType(entity.getNoticeType())
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
