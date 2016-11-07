package org.educama;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.camunda.bpm.spring.boot.starter.SpringBootProcessApplication;

/*
 * Note that extending SpringBootProcessApplication may cause that some Camunda-auto-detect-features won't work.
 * For example define a process.xml will be necessary in some cases.
 * In general working with camunda-bpm-spring-boot-starter-webapp will be more according to Camunda documentation
 * instead of camunda-bpm-spring-boot-starter-webapp documentation.
 */
@SpringBootApplication
public class EducamaApplication extends SpringBootProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(EducamaApplication.class, args);
	}
}
