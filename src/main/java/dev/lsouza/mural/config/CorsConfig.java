package dev.lsouza.mural.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // libera para todas as rotas
                        .allowedOrigins("http://localhost:4200") // libera o front-end
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // libera esses métodos
                        .allowedHeaders("*"); // libera todos os headers
            }
        };
    }
}

