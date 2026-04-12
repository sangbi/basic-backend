package com.basic.backend.core.security.authorization;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class MenuAuthorizationFilter extends OncePerRequestFilter {

    private final MenuAuthorizationService menuAuthorizationService;

    public MenuAuthorizationFilter(MenuAuthorizationService menuAuthorizationService) {
        this.menuAuthorizationService = menuAuthorizationService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        if (!requestUri.startsWith("/admin/")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (isExcluded(requestUri)) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getAuthorities() == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String roleCode = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .map(authority -> authority.replace("ROLE_", ""))
                .orElse(null);

        if (roleCode == null) {
            throw new AccessDeniedException("권한 정보가 없습니다.");
        }

        boolean allowed = menuAuthorizationService.hasPermission(
                roleCode,
                requestUri,
                request.getMethod()
        );

        if (!allowed) {
            throw new AccessDeniedException("메뉴 권한이 없습니다.");
        }

        filterChain.doFilter(request, response);
    }

    private boolean isExcluded(String requestUri) {
        return requestUri.startsWith("/admin/menu-roles");
    }
}