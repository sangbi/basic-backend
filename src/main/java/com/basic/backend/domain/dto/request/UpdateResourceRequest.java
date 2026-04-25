package com.basic.backend.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateResourceRequest {
    private String title;
    private String content;
    private String status;
    private String pinnedYn;
}
