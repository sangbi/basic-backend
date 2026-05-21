package com.basic.backend.domain.service;

import com.basic.backend.domain.entity.AttachmentEntity;
import com.basic.backend.domain.mapper.AttachmentMapper;
import com.basic.backend.domain.mapper.CleanupMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class CleanupService {

    private final CleanupMapper cleanupMapper;
    private final AttachmentMapper attachmentMapper;

    public CleanupService(CleanupMapper cleanupMapper,
                          AttachmentMapper attachmentMapper) {
        this.cleanupMapper = cleanupMapper;
        this.attachmentMapper = attachmentMapper;
    }

    @Transactional
    public void cleanupOldeDeletedPosts(){
        cleanupNotices();
        cleanupResources();
    }

    private void cleanupNotices() {
        List<Long> noticeIds = cleanupMapper.findOldDeletedNoticeIds();

        for (Long noticeId : noticeIds) {
            cleanupTargetAttachments("NOTICE",noticeId);
            cleanupMapper.deleteNoticeById(noticeId);
        }
    }

    private void cleanupResources() {
        List<Long> resourceIds = cleanupMapper.findOldDeletedResourceIds();

        for (Long resourceId : resourceIds) {
            cleanupTargetAttachments("RESOURCE",resourceId);
            cleanupMapper.deleteResourceById(resourceId);
        }
    }

    private void cleanupTargetAttachments(String targetType,Long targetId) {
        List<Long> attachmentIds = cleanupMapper.findAttachmentIdsByTarget(targetType, targetId);

        cleanupMapper.deleteLinksByTarget(targetType, targetId);

        for (Long attachmentId : attachmentIds) {
            int linkCount = cleanupMapper.countLinksByAttachmentId(attachmentId);

            if(linkCount == 0) {
                deletePhysicalFileAndDbRow(attachmentId);
            }
        }
    }

    private void deletePhysicalFileAndDbRow(Long attachmentId) {
        AttachmentEntity attachment = attachmentMapper.findAttachmentById(attachmentId);

        if(attachment == null) {
            return;
        }

        try {
            Files.deleteIfExists(Path.of(attachment.getFilePath()));
        } catch (IOException e) {
            throw new RuntimeException("첨부파일 물리 삭제에 실패했습니다. attachmentId =" + attachmentId, e);
        }

        cleanupMapper.deleteResourceById(attachmentId);
    }
}
