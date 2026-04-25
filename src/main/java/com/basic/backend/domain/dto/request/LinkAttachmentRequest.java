package com.basic.backend.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LinkAttachmentRequest {
    private Long attachmentId;
    private String targetType;
    private Long targetId;
    private Integer sortOrder;
}