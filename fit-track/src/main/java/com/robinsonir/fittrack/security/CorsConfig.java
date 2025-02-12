package com.robinsonir.fittrack.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Value("#{'${management.endpoints.web.cors.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;

    @Value("#{'${management.endpoints.web.cors.allowed-methods}'.split(',')}")
    private List<String> allowedMethods;

    @Value("#{'${management.endpoints.web.cors.allowed-headers}'.split(',')}")
    private List<String> allowedHeaders;

    @Value("#{'${management.endpoints.web.cors.exposed-headers}'.split(',')}")
    private List<String> expectedHeaders;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(allowedMethods);
        configuration.setAllowedHeaders(allowedHeaders);
        configuration.setExposedHeaders(expectedHeaders);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
