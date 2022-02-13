package ru.geekbrains.FrontService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class FrontServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontServiceApplication.class, args);
	}

}
