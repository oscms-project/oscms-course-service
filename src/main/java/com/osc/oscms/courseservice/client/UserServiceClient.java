package com.osc.oscms.courseservice.client;

import com.osc.oscms.common.dto.user.UserResponse;
import com.osc.oscms.common.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 用户服务Feign客户端
 * 用于课程服务调用用户服务获取用户信息
 */
@FeignClient(name = "user-service", path = "/api")
public interface UserServiceClient {

    /**
     * 根据用户ID获取用户信息
     */
    @GetMapping("/users/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable("userId") String userId);

    /**
     * 批量获取用户信息
     */
    @PostMapping("/users/batch")
    ApiResponse<List<UserResponse>> getUsersByIds(@RequestBody List<String> userIds);

    /**
     * 验证用户是否存在且具有指定角色
     */
    @GetMapping("/users/{userId}/role/{role}")
    ApiResponse<Boolean> hasUserRole(@PathVariable("userId") String userId,
            @PathVariable("role") String role);
}
