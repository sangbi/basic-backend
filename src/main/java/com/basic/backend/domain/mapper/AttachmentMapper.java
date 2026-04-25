package com.basic.backend.domain.mapper;

import com.basic.backend.domain.entity.AttachmentEntity;
import com.basic.backend.domain.entity.AttachmentLinkEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttachmentMapper {
    void insertAttachment(AttachmentEntity entity);
    AttachmentEntity findAttachmentById(Long id);

    void insertAttachmentLink(AttachmentLinkEntity entity);

    List<AttachmentEntity> findAttachmentsByTarget(
            @Param("targetType") String targetType,
            @Param("targetId") Long targetId
    );

    void deleteAttachmentLink(@Param("attachmentId") Long attachmentId,
                              @Param("targetType") String targetType,
                              @Param("targetId") Long targetId);

    int countLinksByAttachmentId(Long attachmentId);

    void deleteAttachmentLinkByAttachmentIdAndTarget(
            @Param("attachmentId") Long attachmentId,
            @Param("targetType") String targetType,
            @Param("targetId") Long targetId
    );

    void deleteAttachmentById(Long attachmentId);
}