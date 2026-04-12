package com.basic.backend.domain.mapper;

import com.basic.backend.domain.entity.LoginHistoryEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginHistoryMapper {
    void insert(LoginHistoryEntity entity);
    void updateLogoutBySessionKey(String sessionKey);
    List<LoginHistoryEntity> findAll();
    void updateLatestLogoutByUserId(String userId);
    long countTodaySuccess();
    long countTodayFail();
}