package com.basic.backend.domain.service;

import com.basic.backend.domain.dto.response.LoginHistoryReponse;
import com.basic.backend.domain.entity.LoginHistoryEntity;
import com.basic.backend.domain.mapper.LoginHistoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoginHistoryService {

    private final LoginHistoryMapper loginHistoryMapper;

    public LoginHistoryService(LoginHistoryMapper loginHistoryMapper) {
        this.loginHistoryMapper = loginHistoryMapper;
    }

    @Transactional
    public void saveSuccess(String userId, String ipAdress, String userAgent, String sessionKey) {
        LoginHistoryEntity entity = LoginHistoryEntity.builder()
                .userId(userId)
                .loginResult("SUCCESS")
                .ipAddress(ipAdress)
                .userAgent(userAgent)
                .sessionKey(sessionKey)
                .build();
        loginHistoryMapper.insert(entity);
    }

    @Transactional
    public void saveFail(String userId, String ipAdress, String userAgent, String sessionKey) {
        LoginHistoryEntity entity = LoginHistoryEntity.builder()
                .userId(userId)
                .loginResult("Fail")
                .ipAddress(ipAdress)
                .userAgent(userAgent)
                .sessionKey(sessionKey)
                .build();
        loginHistoryMapper.insert(entity);
    }

    @Transactional
    public void logout(String seeeionKey) {
        loginHistoryMapper.updateLogoutBySessionKey(seeeionKey);
    }

    public List<LoginHistoryReponse> findAll() {
        return loginHistoryMapper.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private LoginHistoryReponse toResponse(LoginHistoryEntity entity) {
        return LoginHistoryReponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .loginResult(entity.getLoginResult())
                .loginAt(entity.getLoginAt())
                .logoutAt(entity.getLogoutAt())
                .ipAddress(entity.getIpAddress())
                .userAgent(entity.getUserAgent())
                .failReason(entity.getFailReason())
                .sessionKey(entity.getSessionKey())
                .build();
    }

    @Transactional
    public void logoutByUserId(String userId) {
        loginHistoryMapper.updateLatestLogoutByUserId(userId);
    }

    public long countTodaySuccess() {
        return loginHistoryMapper.countTodaySuccess();
    }

    public long countTodayFail() {
        return loginHistoryMapper.countTodayFail();
    }
}
