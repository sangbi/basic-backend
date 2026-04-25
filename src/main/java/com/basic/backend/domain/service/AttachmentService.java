package com.basic.backend.domain.service;

import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.core.config.FileProperties;
import com.basic.backend.domain.dto.response.AttachmentResponse;
import com.basic.backend.domain.entity.AttachmentEntity;
import com.basic.backend.domain.entity.AttachmentLinkEntity;
import com.basic.backend.domain.mapper.AttachmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentService {

    private final AttachmentMapper attachmentMapper;
    private final FileProperties fileProperties;

    public AttachmentService(AttachmentMapper attachmentMapper, FileProperties fileProperties) {
        this.attachmentMapper = attachmentMapper;
        this.fileProperties = fileProperties;
    }

    private static final long MAX_FILE_SIZE = 20L * 1024 * 1024; // 20MB

    private static final java.util.Set<String> ALLOWED_EXTENSIONS = java.util.Set.of(
            "jpg", "jpeg", "png", "gif", "webp",
            "pdf",
            "doc", "docx",
            "xls", "xlsx",
            "ppt", "pptx",
            "hwp", "hwpx",
            "zip"
    );

    @Transactional
    public AttachmentResponse upload(MultipartFile file) {
        validateFile(file);

        try {
            String originalFileName = file.getOriginalFilename();
            String extension = extractExtension(originalFileName);
            String storedFileName = UUID.randomUUID() + (extension.isBlank() ? "" : "." + extension);

            Path uploadDir = Paths.get(fileProperties.getUploadDir());
            Files.createDirectories(uploadDir);

            Path targetPath = uploadDir.resolve(storedFileName);
            file.transferTo(targetPath.toFile());

            AttachmentEntity entity = new AttachmentEntity();
            entity.setOriginalFileNm(originalFileName);
            entity.setStoredFileNm(storedFileName);
            entity.setFilePath(targetPath.toString());
            entity.setFileExtension(extension);
            entity.setContentType(file.getContentType());
            entity.setFileSize(file.getSize());
            entity.setStatus("ACTIVE");
            entity.setCreatedBy(SecurityUtil.getCurrentUserId());

            attachmentMapper.insertAttachment(entity);

            return AttachmentResponse.builder()
                    .id(entity.getId())
                    .originalFileNm(entity.getOriginalFileNm())
                    .contentType(entity.getContentType())
                    .fileSize(entity.getFileSize())
                    .url("/admin/attachments/" + entity.getId() + "/view")
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드에 실패했습니다.", e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("업로드할 파일이 없습니다.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("파일 크기는 20MB를 초과할 수 없습니다.");
        }

        String extension = extractExtension(file.getOriginalFilename()).toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new RuntimeException("허용되지 않은 파일 형식입니다.");
        }
    }

    @Transactional
    public void linkToTarget(Long attachmentId, String targetType, Long targetId, int sortOrder) {
        AttachmentLinkEntity entity = new AttachmentLinkEntity();
        entity.setAttachmentId(attachmentId);
        entity.setTargetType(targetType);
        entity.setTargetId(targetId);
        entity.setSortOrder(sortOrder);
        entity.setCreatedBy(SecurityUtil.getCurrentUserId());

        attachmentMapper.insertAttachmentLink(entity);
    }

    public List<AttachmentResponse> findByTarget(String targetType, Long targetId) {
        return attachmentMapper.findAttachmentsByTarget(targetType, targetId).stream()
                .map(file -> AttachmentResponse.builder()
                        .id(file.getId())
                        .originalFileNm(file.getOriginalFileNm())
                        .contentType(file.getContentType())
                        .fileSize(file.getFileSize())
                        .build())
                .toList();
    }

    public AttachmentEntity findEntityById(Long id) {
        return attachmentMapper.findAttachmentById(id);
    }

    @Transactional
    public void unlinkFromTarget(Long attachmentId,String targetType,Long targetId){
        attachmentMapper.deleteAttachmentLink(attachmentId,targetType,targetId);
    }

    @Transactional
    public void removeAttachmentFromTarget(Long attachmentId, String targetType, Long targetId) {
        AttachmentEntity attachment = attachmentMapper.findAttachmentById(attachmentId);
        if (attachment == null) {
            return;
        }

        attachmentMapper.deleteAttachmentLinkByAttachmentIdAndTarget(
                attachmentId,
                targetType,
                targetId
        );

        int linkCount = attachmentMapper.countLinksByAttachmentId(attachmentId);

        if (linkCount == 0) {
            try {
                Path path = Paths.get(attachment.getFilePath());
                Files.deleteIfExists(path);
            } catch (IOException e) {
                throw new RuntimeException("실제 파일 삭제에 실패했습니다.", e);
            }

            attachmentMapper.deleteAttachmentById(attachmentId);
        }
    }

    private String extractExtension(String originalFileNm) {
        if (originalFileNm == null || !originalFileNm.contains(".")) {
            return "";
        }

        return originalFileNm.substring(originalFileNm.lastIndexOf('.') + 1);
    }
}