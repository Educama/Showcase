package org.educama.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuration for CORS (Cross-Origin Request Surgery)
 * This is required to allow request from other hosts than the one the backend service runs on.
 * Since we are serving the frontend from a different location, this is required to allow those requests.
 */
@Configuration
public class CorsConfiguration {

    /**
     * Add CORS filter to application context.
     *
     * @param corsProperties the CORS properties to use for CORS configuration
     * @return the configured CORS filter
     */
    @Bean
    public CorsFilter corsFilter(CorsProperties corsProperties) {
        org.springframework.web.cors.CorsConfiguration config = corsProperties.getConfig();
        config.setAllowedMethods(corsProperties.getAllowedMethods());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(corsProperties.getUrlMapping(), config);
        return new CorsFilter(source);
    }
}
