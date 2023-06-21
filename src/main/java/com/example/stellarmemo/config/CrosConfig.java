package com.example.stellarmemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrosConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("http://localhost:8080/")
            .allowedMethods("GET", "POST", "HEAD", "DELETE", "OPTIONS", "PUT")
            .allowCredentials(true)
            .maxAge(3600)
            .allowedHeaders("*");
  }

}
