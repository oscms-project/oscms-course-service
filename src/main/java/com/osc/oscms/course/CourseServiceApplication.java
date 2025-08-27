package com.osc.oscms.course;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.cloud.openfeign.EnableFeignClients; // 将来需要时启用

@SpringBootApplication
@MapperScan("com.osc.oscms.course.mapper") // 指向 course-service 自己的 mapper 包
// @EnableFeignClients(basePackages = "com.osc.oscms.course.client") // 将来需要时启用
public class CourseServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseServiceApplication.class, args);
    }
}