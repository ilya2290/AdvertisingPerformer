package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfigurator {

    /**
     * Configures Cross-Origin Resource Sharing (CORS) for specific endpoints within the application.
     * CORS is crucial for managing and securing how resources are accessed from different domains
     * in a web application environment.
     *
     * @return A WebMvcConfigurer object for CORS configuration.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer(){

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**").allowedOrigins("http://localhost:3000/");
            }
        };
    }
}
