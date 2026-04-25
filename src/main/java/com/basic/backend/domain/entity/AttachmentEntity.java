package com.basic.backend.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttachmentEntity {
    private Long id;
    private String originalFileNm;
    private String storedFileNm;
    private String filePath;
    private String fileExtension;
    private String contentType;
    private Long fileSize;
    private String status;
    private LocalDateTime createdAt;
    private String createdBy;
}