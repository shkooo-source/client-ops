package com.clientops.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로(/**)에 대해
        registry.addMapping("/**")
            // React 개발 서버 주소(5173)로부터의 요청을 허용
            .allowedOrigins("http://localhost:5173")
            // 허용할 HTTP 메서드들
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            // 모든 헤더 허용
            .allowedHeaders("*")
            // 쿠키 등 인증 정보 허용 여부
            .allowCredentials(true);
    }
}
