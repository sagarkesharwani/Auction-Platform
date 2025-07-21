package com.Auction.Platform.Utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import java.nio.file.Paths;
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // allow all endpoints
                .allowedOrigins("http://localhost:4200") // allow Angular dev origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // what methods you allow
                .allowedHeaders("*") // allow any headers
                .allowCredentials(true);
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/images/**")
        .addResourceLocations("file:C:/Users/msi/Desktop/project/backend/Auction-Platform-service/uploads/images/");
    }
}

