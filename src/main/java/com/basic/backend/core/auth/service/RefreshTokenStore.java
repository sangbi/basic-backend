package com.basic.backend.core.auth.service;

import com.basic.backend.core.auth.model.RefreshToken;

public interface RefreshTokenStore {
    void save(RefreshToken refreshToken);
    RefreshToken findByRefreshToken(String refreshToken);
    RefreshToken findByUserId(String userId);
    void deleteByUserId(String userId);
}