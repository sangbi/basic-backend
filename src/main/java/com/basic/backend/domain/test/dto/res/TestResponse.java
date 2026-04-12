package com.basic.backend.domain.test.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestResponse {
    private Long id;
    private String message;
}