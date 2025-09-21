package com.coverletter.coverletter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /uploads/** URL로 요청이 오면,
        // file:///E:/Dev/Programming/coverletter/coverletter/uploads/profileImages/ 경로에서 파일을 찾아 제공합니다.
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///" + uploadDir + "/");
    }
}