package com.basic.backend.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttachmentLinkEntity {
    private Long id;
    private Long attachmentId;
    private String targetType;
    private Long targetId;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private String createdBy;
}