package com.basic.backend.domain.dto.request;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchUserCondition {
    private String userId;
    private String role;
}
