package com.basic.backend.domain.service;

import com.basic.backend.domain.dto.response.ActivityLogResponse;
import com.basic.backend.domain.entity.ActivityLogEntity;
import com.basic.backend.domain.mapper.ActivityLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivityLogService {

    private final ActivityLogMapper activityLogMapper;

    public ActivityLogService(ActivityLogMapper activityLogMapper) {
        this.activityLogMapper = activityLogMapper;
    }

    @Transactional
    public void save(
            String userId,
            String actionType,
            String httpMethod,
            String requestUri,
            String ipAdress,
            String userAgent,
            String responseCode,
            String requestBodySummary
    ) {
        ActivityLogEntity entity = ActivityLogEntity.builder()
                .userId(userId)
                .actionType(actionType)
                .httpMethod(httpMethod)
                .requestUri(requestUri)
                .ipAddress(ipAdress)
                .userAgent(userAgent)
                .responseCode(responseCode)
                .requestBodySummary(requestBodySummary)
                .build();
        activityLogMapper.insert(entity);
    }

    public List<ActivityLogResponse> findAll() {
        return activityLogMapper.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private ActivityLogResponse toResponse(ActivityLogEntity entity) {
        return ActivityLogResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .actionType(entity.getActionType())
                .httpMethod(entity.getHttpMethod())
                .requestUri(entity.getRequestUri())
                .ipAddress(entity.getIpAddress())
                .userAgent(entity.getUserAgent())
                .responseCode(entity.getResponseCode())
                .requestBodySummary(entity.getRequestBodySummary())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public long countToday() {
        return activityLogMapper.countToday();
    }
}
