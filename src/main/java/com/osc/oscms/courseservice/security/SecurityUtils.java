package com.osc.oscms.courseservice.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 安全工具类
 */
public class SecurityUtils {

    /**
     * 获取当前登录用户ID
     */
    public static String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * 获取当前用户角色
     */
    public static String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if (!authorities.isEmpty()) {
                return authorities.iterator().next().getAuthority();
            }
        }
        return null;
    }

    /**
     * 检查当前用户是否是教师
     */
    public static boolean isTeacher() {
        String role = getCurrentUserRole();
        return "ROLE_TEACHER".equals(role);
    }

    /**
     * 检查当前用户是否是助教
     */
    public static boolean isTA() {
        String role = getCurrentUserRole();
        return "ROLE_TA".equals(role);
    }

    /**
     * 检查当前用户是否是学生
     */
    public static boolean isStudent() {
        String role = getCurrentUserRole();
        return "ROLE_STUDENT".equals(role);
    }

    /**
     * 检查当前用户是否有权限（教师或助教）
     */
    public static boolean hasTeachingPermission() {
        return isTeacher() || isTA();
    }

    /**
     * 检查当前用户是否是指定用户ID
     */
    public static boolean isCurrentUser(String userId) {
        String currentUserId = getCurrentUserId();
        return currentUserId != null && currentUserId.equals(userId);
    }
}
