package ru.geekbrains.lesson7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class Lesson7Application {

	public static void main(String[] args) {
		SpringApplication.run(Lesson7Application.class, args);
	}

}
