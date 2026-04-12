package com.basic.backend.domain.test.api.payload;

import com.basic.backend.domain.test.dto.res.TestResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class TestPayload {

    @Data
    @Builder
    public static class TestPayloadResponse {
        TestResponse result;
    }

    @Data
    @AllArgsConstructor
    public static class TestPayloadRequest {
        @NotBlank(message = "message는 필수입니다.")
        private String message;
    }
}
