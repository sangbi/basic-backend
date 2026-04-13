package com.basic.backend.domain.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchUserRequest {
    private int offset;
    private int limit;
    private String userId;
}
