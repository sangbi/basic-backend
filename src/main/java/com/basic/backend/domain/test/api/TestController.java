package com.basic.backend.domain.test.api;

import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.domain.test.api.payload.TestPayload;
import com.basic.backend.domain.test.dto.req.TestRequest;
import com.basic.backend.domain.test.service.TestService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("admin/test1")
    public ApiResponse<TestPayload.TestPayloadResponse> adminTest1(
            @RequestParam Long id
    ) {
        return ApiResponse.result(
                TestPayload.TestPayloadResponse.builder().result(testService.getMessage(id)).build()
        );

    }

    @PostMapping("admin/test2")
    public ApiResponse<TestPayload.TestPayloadResponse> adminTest2(
            @RequestBody @Valid TestPayload.TestPayloadRequest payload
    ) {
        TestRequest data = TestRequest.builder().message(payload.getMessage()).build();
        return ApiResponse.result(
                TestPayload.TestPayloadResponse.builder().result(testService.create(data)).build()
        );
    }

    @GetMapping("user/test1")
    public ApiResponse<TestPayload.TestPayloadResponse> userTest1(
            @RequestParam Long id
    ) {
        return ApiResponse.result(
                TestPayload.TestPayloadResponse.builder().result(testService.getMessage(id)).build()
        );

    }

    @PostMapping("user/test2")
    public ApiResponse<TestPayload.TestPayloadResponse> userTest2(
            @RequestBody @Valid TestPayload.TestPayloadRequest payload
    ) {
        TestRequest data = TestRequest.builder().message(payload.getMessage()).build();
        return ApiResponse.result(
                TestPayload.TestPayloadResponse.builder().result(testService.create(data)).build()
        );
    }
}