package com.osc.oscms.courseservice.util;

/**
 * JWT Token持有者工具类
 * 用于在请求上下文中传递JWT token，解决微服务间调用时的认证问题
 */
public class JwtTokenHolder {

    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();

    /**
     * 设置当前线程的JWT token
     */
    public static void setCurrentToken(String token) {
        tokenHolder.set(token);
    }

    /**
     * 获取当前线程的JWT token
     */
    public static String getCurrentToken() {
        return tokenHolder.get();
    }

    /**
     * 清除当前线程的JWT token
     */
    public static void clearCurrentToken() {
        tokenHolder.remove();
    }
}
