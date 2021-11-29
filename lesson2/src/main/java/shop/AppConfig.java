package shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
@ComponentScan("shop")
public class AppConfig {
    @Bean
    public List<Product> productList() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "PSU", 100.0));
        products.add(new Product(2, "Motherboard", 200.0));
        products.add(new Product(3, "Memory RAM", 300.0));
        products.add(new Product(4, "CPU", 400.0));
        products.add(new Product(5, "Graphic Card", 500.0));
        return products;
    }

    @Bean
    public Map<Product, Integer> productMap() {
        return new LinkedHashMap<>();
    }
}
