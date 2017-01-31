package org.educama;

import org.educama.configuration.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({CorsProperties.class})
public class EducamaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EducamaApplication.class, args);
	}
}
