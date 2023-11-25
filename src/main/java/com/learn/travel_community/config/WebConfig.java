package com.learn.travel_community.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${file.path}")
    private String uploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // view 에서 접근할 경로
        String resourcePath = "/upload/**";
        String savePath = "file:///" + uploadFolder;    // 실제 파일 저장 경로(win)

        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
    }
}