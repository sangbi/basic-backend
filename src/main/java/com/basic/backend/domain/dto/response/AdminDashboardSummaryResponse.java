package com.basic.backend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminDashboardSummaryResponse {
    private long todayLoginSuccessCount;
    private long todayLoginFailCount;
    private long activeSessionCount;
    private long todayActivityLogCount;
}
