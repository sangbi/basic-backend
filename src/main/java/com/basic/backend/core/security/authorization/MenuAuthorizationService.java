package com.basic.backend.core.security.authorization;

import com.basic.backend.domain.mapper.MenuRoleMapper;
import org.springframework.stereotype.Service;

@Service
public class MenuAuthorizationService {

    private final MenuRoleMapper menuRoleMapper;

    public MenuAuthorizationService(MenuRoleMapper menuRoleMapper) {
        this.menuRoleMapper = menuRoleMapper;
    }

    public boolean hasPermission(String roleCode, String requestUri, String method) {
        String permissionType = resolvePermissionType(method, requestUri);

        if (permissionType == null) {
            return false;
        }

        String normalizedApiPath = normalizeApiPath(requestUri);

        String permissionYn = menuRoleMapper.findPermissionYn(
                permissionType, roleCode,
                normalizedApiPath
        );

        return "Y".equalsIgnoreCase(permissionYn);
    }

    private String resolvePermissionType(String method, String requestUri) {
        String upperMethod = method.toUpperCase();

        if ("POST".equals(upperMethod) && requestUri.endsWith("/search")) {
            return "READ";
        }

        return switch (upperMethod) {
            case "GET" -> "READ";
            case "POST" -> "CREATE";
            case "PUT", "PATCH" -> "UPDATE";
            case "DELETE" -> "DELETE";
            default -> null;
        };
    }

    private String normalizeApiPath(String requestUri) {
        if (requestUri == null || requestUri.isBlank()) {
            return requestUri;
        }

        String normalized = requestUri;

//        normalized = normalized.replaceAll("/summary$", "");
        normalized = normalized.replaceAll("/me$", "");
        normalized = normalized.replaceAll("/active$", "");
        normalized = normalized.replaceAll("/search$", "");
        normalized = normalized.replaceAll("/\\d+$", "");

        return normalized;
    }
}