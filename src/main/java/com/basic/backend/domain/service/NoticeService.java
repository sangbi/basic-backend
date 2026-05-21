package com.basic.backend.domain.service;

import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.core.exception.BasicException;
import com.basic.backend.core.logging.ActivityLogAction;
import com.basic.backend.core.paging.PageRequest;
import com.basic.backend.core.paging.PageResponse;
import com.basic.backend.core.paging.PageResult;
import com.basic.backend.core.response.ErrorCode;
import com.basic.backend.domain.dto.request.CreateNoticeRequest;
import com.basic.backend.domain.dto.request.SearchNoticeCondition;
import com.basic.backend.domain.dto.request.SearchNoticeRequest;
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

    public PageResponse<NoticeResponse> search(PageRequest<SearchNoticeCondition> request) {
        SearchNoticeRequest mapperRequest = SearchNoticeRequest.builder()
                .offset(request.getOffset())
                .limit(request.getLimit())
                .title(request.getCondition().getTitle())
                .build();

        List<NoticeEntity> list = noticeMapper.findAllPaged(mapperRequest);
        long totlaCount = noticeMapper.countAll(mapperRequest);

        List<NoticeResponse> items = list.stream()
                .map(notice -> NoticeResponse.builder()
                        .id(notice.getId())
                        .title(notice.getTitle())
                        .content(notice.getContent())
                        .noticeType(notice.getNoticeType())
                        .status(notice.getStatus())
                        .pinnedYn(notice.getPinnedYn())
                        .viewCnt(notice.getViewCnt())
                        .createdAt(notice.getCreatedAt())
                        .createdBy(notice.getCreatedBy())
                        .updatedAt(notice.getUpdatedAt())
                        .updatedBy(notice.getUpdatedBy())
                        .build()
                ).toList();

        return PageResult.of(items, request, totlaCount);
    }

    public NoticeResponse findById(Long id) {
        return toResponse(noticeMapper.findById(id));
    }

    @Transactional
    public NoticeResponse findActiveById(Long id) {
        noticeMapper.increaseViewCount(id);
        NoticeEntity entity = noticeMapper.findActiveById(id);

        if (entity == null) {
            throw new BasicException(ErrorCode.DATA_NOT_FOUND);
        }

        return toResponse(entity);
    }

    @Transactional
    @ActivityLogAction(actionType = "NOTICE_CREATE",message = "공지사항 등록")
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
    @ActivityLogAction(actionType = "NOTICE_UPDATE",message = "공지사항 수정")
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

    @Transactional
    @ActivityLogAction(actionType = "NOTICE_DELETE",message = "공지사항 삭제")
    public void delete(Long id) {
        NoticeEntity entity = NoticeEntity.builder()
                .id(id)
                .updatedBy(SecurityUtil.getCurrentUserId())
                .build();

        noticeMapper.deleteById(entity);
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
