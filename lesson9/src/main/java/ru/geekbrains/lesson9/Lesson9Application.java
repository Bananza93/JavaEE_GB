package ru.geekbrains.lesson9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.SessionScope;
import ru.geekbrains.lesson9.bean.Cart;
import ru.geekbrains.lesson9.dto.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@SpringBootApplication
public class Lesson9Application {

	public static void main(String[] args) {
		SpringApplication.run(Lesson9Application.class, args);
	}

}
