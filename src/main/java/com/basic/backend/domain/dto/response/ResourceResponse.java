package com.basic.backend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ResourceResponse {
    private Long id;
    private String title;
    private String content;
    private String status;
    private String pinnedYn;
    private Integer viewCnt;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
