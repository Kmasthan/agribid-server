package com.agribid_server.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
        .allowedOrigins("http://localhost:4200") // Allowing specific origin (e.g., React app)
        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Allowed methods
        .allowedHeaders("*") // Allowing all headers
        .allowCredentials(true) // Allowing credentials
        .maxAge(3600); 
	} 
}
