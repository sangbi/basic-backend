package com.basic.backend.core.audit;

import com.basic.backend.core.auth.util.SecurityUtil;

public class AuditUtil {

    private AuditUtil() {
    }

    public static String getCurrentUser() {
        String userId = SecurityUtil.getCurrentUserId();
        return userId == null ? "SYSTEM" : userId;
    }
}