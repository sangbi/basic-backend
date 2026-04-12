package com.basic.backend.domain.dto.request;

import com.basic.backend.core.paging.PageRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchUserCondition {
    private String userId;
    private String role;
}
