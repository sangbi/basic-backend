package com.basic.backend.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCodeGroupRequest {
    private String groupNm;
    private String description;
    private String status;
}