package com.rtrnonato.library_management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração de CORS da aplicação.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
				.allowedOriginPatterns("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowCredentials(true);
	}
}
