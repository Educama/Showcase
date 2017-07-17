package org.educama.api.config;

import org.educama.api.logging.RequestLoggingFilter;
import org.educama.api.logging.ResponseLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Logging configuration to provide beans in context to log requests and responses.
 */
@Configuration
// @Profile("log-requests")
public class LoggingConfiguration {

    /**
     * Add filter to log requests.
     *
     * @return the {@link RequestLoggingFilter}
     */
    @Bean
    public RequestLoggingFilter requestLoggingFilter() {
        return new RequestLoggingFilter();
    }

    /**
     * Add filter to log responses.
     *
     * @return the {@link ResponseLoggingFilter}
     */
    @Bean
    public ResponseLoggingFilter responseLoggingFilter() {
        return new ResponseLoggingFilter();
    }
}
