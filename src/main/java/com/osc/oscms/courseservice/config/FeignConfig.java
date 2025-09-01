package com.osc.oscms.courseservice.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import com.osc.oscms.common.util.JwtTokenHolder;

/**
 * Feign客户端配置
 * 用于在微服务间调用时传递JWT token
 */
@Configuration
public class FeignConfig {

    /**
     * 配置Feign请求拦截器，自动添加JWT token到请求头
     */
    @Bean
    public RequestInterceptor feignRequestInterceptor() {
        return template -> {
            // 从Security上下文获取当前认证信息
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated() &&
                    !"anonymousUser".equals(authentication.getName())) {

                // 从Security上下文中获取JWT token
                // 这里我们需要从请求上下文中获取原始token
                // 由于Feign调用是在同一个线程中，我们可以通过ThreadLocal来传递token
                String token = JwtTokenHolder.getCurrentToken();

                if (token != null) {
                    // 添加Authorization头
                    template.header("Authorization", "Bearer " + token);
                }
            }
        };
    }
}
