package org.educama;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class to bootstrap the Educama Spring Boot application.
 */
@SpringBootApplication
@SuppressWarnings("checkstyle:hideutilityclassconstructor")
public class EducamaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EducamaApplication.class, args);
    }
}
