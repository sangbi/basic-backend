package com.basic.backend.domain.test.service;

import com.basic.backend.domain.test.dto.req.TestRequest;
import com.basic.backend.domain.test.dto.res.TestResponse;
import com.basic.backend.domain.test.repository.mapper.TestMapper;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private final TestMapper testMapper;

    public TestService(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    public TestResponse getMessage(Long id) {
        return TestResponse.builder()
                .message(testMapper.findById(id))
                .build();
    }

    public TestResponse create(TestRequest request) {
        testMapper.insert(request.getMessage());
        return TestResponse.builder()
                .message(request.getMessage())
                .build();
    }
}