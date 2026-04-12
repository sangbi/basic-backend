package com.basic.backend.domain.mapper;

import com.basic.backend.domain.entity.UserSessionEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserSessionMapper {
    void insert(UserSessionEntity entity);
    void updateLastAccessAt(String sessionKey);
    void updateLogout(String sessionKey);
    List<UserSessionEntity> findActiveSessions();
    void updateLatestActiveLogoutByUserId(String userId);
    long countActiveSessions();
}