package com.basic.backend.domain.service;

import com.basic.backend.domain.dto.response.AdminDashboardSummaryResponse;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardService {

    private final LoginHistoryService loginHistoryService;
    private final UserSessionService userSessionService;
    private final ActivityLogService activityLogService;

    public AdminDashboardService(
            LoginHistoryService loginHistoryService,
            UserSessionService userSessionService,
            ActivityLogService activityLogService
    ) {
        this.loginHistoryService = loginHistoryService;
        this.userSessionService = userSessionService;
        this.activityLogService = activityLogService;
    }

    public AdminDashboardSummaryResponse getSummary() {
        return AdminDashboardSummaryResponse.builder()
                .todayLoginSuccessCount(loginHistoryService.countTodaySuccess())
                .todayLoginFailCount(loginHistoryService.countTodayFail())
                .activeSessionCount(userSessionService.countActiveSessions())
                .todayActivityLogCount(activityLogService.countToday())
                .build();
    }
}
