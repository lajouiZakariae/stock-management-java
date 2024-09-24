package com.example.stock_management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapping the URL path `/uploads/**` to the actual folder on disk
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:./storage/uploads/");
    }
}

