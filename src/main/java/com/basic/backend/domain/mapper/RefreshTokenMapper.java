package com.basic.backend.domain.mapper;

import com.basic.backend.domain.entity.RefreshTokenEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenMapper {
    void insert(RefreshTokenEntity entity);
    void update(RefreshTokenEntity entity);
    RefreshTokenEntity findByRefreshToken(String refreshToken);
    RefreshTokenEntity findByUserId(String userId);
    void deleteByUserId(String userId);
}