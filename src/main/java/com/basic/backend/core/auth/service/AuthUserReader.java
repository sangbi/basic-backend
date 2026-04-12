package com.basic.backend.core.auth.service;

import com.basic.backend.core.auth.model.AuthUser;

public interface AuthUserReader {
    AuthUser findByUserId(String userId);
    void save(AuthUser authUser);
}