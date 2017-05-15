package org.educama;

import org.educama.configuration.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Main application class to bootstrap the Educama Spring Boot application.
 */
@SpringBootApplication
@EnableConfigurationProperties({CorsProperties.class})
@SuppressWarnings("checkstyle:hideutilityclassconstructor")
public class EducamaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EducamaApplication.class, args);
    }
}
