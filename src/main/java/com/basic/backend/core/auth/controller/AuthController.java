package com.basic.backend.core.auth.controller;

import com.basic.backend.core.auth.dto.request.LoginRequest;
import com.basic.backend.core.auth.dto.request.RefreshTokenRequest;
import com.basic.backend.core.auth.dto.request.RegisterRequest;
import com.basic.backend.core.auth.dto.response.LoginResponse;
import com.basic.backend.core.auth.dto.response.MeResponse;
import com.basic.backend.core.auth.dto.response.TokenRefreshResponse;
import com.basic.backend.core.auth.service.AuthService;
import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.core.exception.BasicException;
import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.core.response.ErrorCode;
import com.basic.backend.core.util.RequestInfoUtil;
import com.basic.backend.domain.dto.request.AuthTokenResult;
import com.basic.backend.domain.service.ActivityLogService;
import com.basic.backend.domain.service.LoginHistoryService;
import com.basic.backend.domain.service.UserSessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final LoginHistoryService loginHistoryService;
    private final UserSessionService userSessionService;
    private final ActivityLogService activityLogService;

    public AuthController(AuthService authService,
                          LoginHistoryService loginHistoryService,
                          UserSessionService userSessionService,
                          ActivityLogService activityLogService) {
        this.authService = authService;
        this.loginHistoryService = loginHistoryService;
        this.userSessionService = userSessionService;
        this.activityLogService = activityLogService;
    }

    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody @Valid RegisterRequest request, HttpServletRequest httpRequest) {
        authService.register(request);

        activityLogService.save(
                SecurityUtil.getCurrentUserId(),
                "USER_CREATE",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                RequestInfoUtil.getClientIp(httpRequest),
                RequestInfoUtil.getUserAgent(httpRequest),
                "200",
                "userId=" + request.getUserId()
        );
        return ApiResponse.result("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody @Valid LoginRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse response
    ) {
        String ipAdress = RequestInfoUtil.getClientIp(httpRequest);
        String userAgent = RequestInfoUtil.getUserAgent(httpRequest);

        AuthTokenResult result = authService.login(request,ipAdress,userAgent);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", result.getRefreshToken())
                .httpOnly(true)
                .secure(false) // prod에서는 true
                .sameSite("Lax") // 프론트/백엔드 배치에 따라 조정
                .path("/")
                .maxAge(60 * 60 * 24 * 14)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.ok(
                ApiResponse.result(
                        LoginResponse.builder()
                                .accessToken(result.getAccessToken())
                                .build()
                )
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenRefreshResponse>> refresh(
            @CookieValue(value = "refreshToken", required = false) String refreshToken
    ) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new BasicException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        TokenRefreshResponse result = authService.refresh(refreshToken);

        return ResponseEntity.ok(ApiResponse.result(result));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(
            HttpServletResponse response
    ) {
        String currentUserId = SecurityUtil.getCurrentUserId();

        if (currentUserId != null) {
            loginHistoryService.logoutByUserId(currentUserId);
            userSessionService.logoutByUserId(currentUserId);
        }
        authService.logout(SecurityUtil.getCurrentUserId());

        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false) // prod에서는 true
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

        return ResponseEntity.ok(ApiResponse.result("로그아웃 성공"));
    }

    @GetMapping("/me")
    public ApiResponse<MeResponse> me() {
        return ApiResponse.result(authService.me());
    }
}