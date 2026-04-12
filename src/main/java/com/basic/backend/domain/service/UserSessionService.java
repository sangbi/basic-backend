package com.basic.backend.domain.service;

import com.basic.backend.domain.dto.response.UserSessionResponse;
import com.basic.backend.domain.entity.UserSessionEntity;
import com.basic.backend.domain.mapper.UserSessionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserSessionService {

    private final UserSessionMapper userSessionMapper;

    public UserSessionService(UserSessionMapper userSessionMapper) {
        this.userSessionMapper = userSessionMapper;
    }

    @Transactional
    public void create(
            String userId,
            String sessionKey,
            String refreshToken,
            String ipAdress,
            String userAgent,
            LocalDateTime expiresAt
            ) {
        UserSessionEntity entity = UserSessionEntity.builder()
                .userId(userId)
                .sessionKey(sessionKey)
                .refreshToken(refreshToken)
                .ipAddress(ipAdress)
                .userAgent(userAgent)
                .status("ACTIVE")
                .expiresAt(expiresAt)
                .build();
        userSessionMapper.insert(entity);
    }

    @Transactional
    public void touch(String sessionKey) {
        userSessionMapper.updateLastAccessAt(sessionKey);
    }

    @Transactional
    public void logout(String sessionKey) {
        userSessionMapper.updateLogout(sessionKey);
    }

    public List<UserSessionResponse> findActiveSessions() {
        return userSessionMapper.findActiveSessions().stream()
                .map(this::toResponse)
                .toList();
    }

    private UserSessionResponse toResponse(UserSessionEntity entity) {
        return UserSessionResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .sessionKey(entity.getSessionKey())
                .ipAddress(entity.getIpAddress())
                .userAgent(entity.getUserAgent())
                .loginAt(entity.getLoginAt())
                .lastAccessAt(entity.getLastAccessAt())
                .logoutAt(entity.getLogoutAt())
                .status(entity.getStatus())
                .expiresAt(entity.getExpiresAt())
                .build();
    }

    @Transactional
    public void logoutByUserId(String userId) {
        userSessionMapper.updateLatestActiveLogoutByUserId(userId);
    }

    public long countActiveSessions() {
        return userSessionMapper.countActiveSessions();
    }
}
