package com.basic.backend.core.util;

import jakarta.servlet.http.HttpServletRequest;

public class RequestInfoUtil {

    private RequestInfoUtil() {
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if(ip == null || ip.isBlank() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if(ip != null && ip.contains(",")) {
            return ip.split(",")[0].trim();
        }

        return ip;
    }

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }
}
