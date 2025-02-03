package com.example.letmovie.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().openapi("3.0.3")
                .info(apiInfo());
    }

    private Info apiInfo() {

        return new Info()
                .title("LetMovie")
                .description("영화관 웹 플랫폼 프로젝트")
                .title("v1");
    }
}
