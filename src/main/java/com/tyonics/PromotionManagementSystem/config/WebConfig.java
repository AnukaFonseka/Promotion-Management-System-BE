package com.tyonics.PromotionManagementSystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = "file:///E:/Learn/Java/PromotionManagementSystem/src/main/resources/static/public/";
        registry.addResourceHandler("/content/**")
                .addResourceLocations(path);
    }
}