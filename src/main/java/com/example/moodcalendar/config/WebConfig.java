package com.example.moodcalendar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 정적 리소스 핸들러에서 /api/** 경로는 제외
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/static/", "classpath:/js/");
        // /api/**는 정적 리소스에 매핑하지 않음!
    }
}
