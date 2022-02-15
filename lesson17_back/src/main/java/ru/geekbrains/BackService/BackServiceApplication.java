package ru.geekbrains.BackService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BackServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackServiceApplication.class, args);
	}
}
