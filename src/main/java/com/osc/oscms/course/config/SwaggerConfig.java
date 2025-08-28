package com.osc.oscms.course.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置类
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OSCMS Course Service API")
                        .description("在线课程管理系统 - 课程服务API文档")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("OSCMS Team")
                                .email("team@oscms.com")));
    }
}



