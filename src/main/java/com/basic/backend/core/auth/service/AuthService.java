package com.basic.backend.core.auth.service;

import com.basic.backend.core.auth.dto.request.LoginRequest;
import com.basic.backend.core.auth.dto.request.RefreshTokenRequest;
import com.basic.backend.core.auth.dto.request.RegisterRequest;
import com.basic.backend.core.auth.dto.response.LoginResponse;
import com.basic.backend.core.auth.dto.response.MeResponse;
import com.basic.backend.core.auth.dto.response.TokenRefreshResponse;
import com.basic.backend.core.auth.model.AuthUser;
import com.basic.backend.core.auth.model.RefreshToken;
import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.core.exception.BasicException;
import com.basic.backend.core.logging.ActivityLogAction;
import com.basic.backend.core.response.ErrorCode;
import com.basic.backend.core.security.jwt.JwtTokenProvider;
import com.basic.backend.domain.dto.request.AuthTokenResult;
import com.basic.backend.domain.service.LoginHistoryService;
import com.basic.backend.domain.service.UserSessionService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthService {

    private final AuthUserReader authUserReader;
    private final RefreshTokenStore refreshTokenStore;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final LoginHistoryService loginHistoryService;
    private final UserSessionService userSessionService;

    public AuthService(
            AuthUserReader authUserReader,
            RefreshTokenStore refreshTokenStore,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider,
            LoginHistoryService loginHistoryService,
            UserSessionService userSessionService
    ) {
        this.authUserReader = authUserReader;
        this.refreshTokenStore = refreshTokenStore;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.loginHistoryService = loginHistoryService;
        this.userSessionService = userSessionService;
    }

    @ActivityLogAction(actionType = "USER_CREATE",message = "사용자 등록")
    public void register(RegisterRequest request) {
        AuthUser existingUser = authUserReader.findByUserId(request.getUserId());
        if (existingUser != null) {
            throw new BasicException(ErrorCode.DUPLICATE_USER_ID); // 이미 존재하는 사용자입니다.
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        AuthUser authUser = AuthUser.builder()
                .userId(request.getUserId())
                .password(encodedPassword)
                .roleId(request.getRoleId())
                .userNm(request.getUserNm())
                .email(request.getEmail())
                .build();

        try {
            authUserReader.save(authUser);
        } catch (DuplicateKeyException e) {
            throw new BasicException(ErrorCode.DUPLICATE_USER_ID);
        }
    }

    public AuthTokenResult login(LoginRequest request, String ipAdress, String userAgent) {
        AuthUser user = authUserReader.findByUserId(request.getUserId());

        if (user == null) {
            throw new BasicException(ErrorCode.USER_NOT_FOUND);
        }
        String accessToken = jwtTokenProvider.createAccessToken(user.getUserId(), user.getRoleCode());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUserId());
        String sessionKey = java.util.UUID.randomUUID().toString();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            loginHistoryService.saveFail(user.getUserId(), ipAdress, userAgent, sessionKey);
            throw new BasicException(ErrorCode.PASSWORD_NOT_MATCH);
        }



        LocalDateTime refreshExpiresAt = jwtTokenProvider.getExpiration(refreshToken)
                        .toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDateTime();

        refreshTokenStore.save(
                RefreshToken.builder()
                        .userId(user.getUserId())
                        .refreshToken(refreshToken)
                        .expiresAt(
                                jwtTokenProvider.getExpiration(refreshToken)
                                        .toInstant()
                                        .atZone(java.time.ZoneId.systemDefault())
                                        .toLocalDateTime()
                        )
                        .build()
        );

        loginHistoryService.saveSuccess(user.getUserId(), ipAdress, userAgent, sessionKey);
        userSessionService.create(user.getUserId(),sessionKey,refreshToken,ipAdress,userAgent,refreshExpiresAt);

        return AuthTokenResult.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .sessionKey(sessionKey)
                .build();
    }

    public TokenRefreshResponse refresh(String refreshToken) {

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BasicException(ErrorCode.INVALID_REFRESH_TOKEN); // 유효하지 않은 refresh token입니다.
        }

        RefreshToken savedToken = refreshTokenStore.findByRefreshToken(refreshToken);

        if (savedToken == null) {
            throw new BasicException(ErrorCode.INVALID_TOKEN); // 저장되지 않은 refresh token입니다.
        }

        if (savedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BasicException(ErrorCode.EXPIRED_TOKEN); // 만료된 refresh token입니다.
        }

        AuthUser user = authUserReader.findByUserId(savedToken.getUserId());

        if (user == null) {
            throw new BasicException(ErrorCode.USER_NOT_FOUND); // 사용자가 존재하지 않습니다.
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(user.getUserId(), user.getRoleCode());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(user.getUserId());

        RefreshToken updateRefreshToken = RefreshToken.builder()
                .userId(user.getUserId())
                .refreshToken(newRefreshToken)
                .expiresAt(
                        jwtTokenProvider.getExpiration(newRefreshToken)
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                )
                .build();

        refreshTokenStore.save(updateRefreshToken);

        return TokenRefreshResponse.builder()
                .accessToken(newAccessToken)
                .build();
    }

    public void logout(String userId) {
        refreshTokenStore.deleteByUserId(userId);
    }

    public MeResponse me() {
        return MeResponse.builder()
                .userId(SecurityUtil.getCurrentUserId())
                .role(SecurityUtil.getCurrentUserRole())
                .build();
    }
}