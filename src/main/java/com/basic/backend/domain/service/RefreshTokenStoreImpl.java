package com.basic.backend.domain.service;

import com.basic.backend.core.auth.model.RefreshToken;
import com.basic.backend.core.auth.service.RefreshTokenStore;
import com.basic.backend.domain.entity.RefreshTokenEntity;
import com.basic.backend.domain.mapper.RefreshTokenMapper;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenStoreImpl implements RefreshTokenStore {

    private final RefreshTokenMapper refreshTokenMapper;

    public RefreshTokenStoreImpl(RefreshTokenMapper refreshTokenMapper) {
        this.refreshTokenMapper = refreshTokenMapper;
    }

    @Override
    public void save(RefreshToken refreshToken) {
        RefreshTokenEntity existing = refreshTokenMapper.findByUserId(refreshToken.getUserId());

        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setUserId(refreshToken.getUserId());
        entity.setRefreshToken(refreshToken.getRefreshToken());
        entity.setExpiresAt(refreshToken.getExpiresAt());

        if (existing == null) {
            refreshTokenMapper.insert(entity);
        } else {
            refreshTokenMapper.update(entity);
        }
    }

    @Override
    public RefreshToken findByRefreshToken(String refreshToken) {
        RefreshTokenEntity entity = refreshTokenMapper.findByRefreshToken(refreshToken);
        if (entity == null) return null;

        return RefreshToken.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .refreshToken(entity.getRefreshToken())
                .expiresAt(entity.getExpiresAt())
                .build();
    }

    @Override
    public RefreshToken findByUserId(String userId) {
        RefreshTokenEntity entity = refreshTokenMapper.findByUserId(userId);
        if (entity == null) return null;

        return RefreshToken.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .refreshToken(entity.getRefreshToken())
                .expiresAt(entity.getExpiresAt())
                .build();
    }

    @Override
    public void deleteByUserId(String userId) {
        refreshTokenMapper.deleteByUserId(userId);
    }
}