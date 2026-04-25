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

        String permissionYn = menuRoleMapper.findPermissionYnByRequestUri(
                roleCode,
                requestUri,
                permissionType
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
}