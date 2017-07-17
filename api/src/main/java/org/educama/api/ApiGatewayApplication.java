package org.educama.api;

import org.educama.api.config.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Main application class to bootstrap the API Gateway Spring Boot application.
 */
@EnableConfigurationProperties(CorsProperties.class)
@EnableZuulProxy
@SpringBootApplication
@SuppressWarnings("checkstyle:hideutilityclassconstructor")
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
