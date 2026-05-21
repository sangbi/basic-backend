package com.basic.backend.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CleanupMapper {
    List<Long> findAttachmentIdsByTarget(
            @Param("targetType") String targetType,
            @Param("targetId") Long targetId
    );

    void deleteLinksByTarget(
            @Param("targetType") String targetType,
            @Param("targetId") Long targetId
    );

    int countLinksByAttachmentId(Long attachmentId);

    void deleteAttachmentById(Long attachmentId);

    List<Long> findOldDeletedNoticeIds();
    void deleteNoticeById(Long id);

    List<Long> findOldDeletedResourceIds();
    void deleteResourceById(Long id);
}
