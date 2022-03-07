package ru.geekbrains.lesson7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
@EnableAsync
public class AppConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Primary
    @Bean
    public FreeMarkerConfigurationFactoryBean factoryBean() {
        FreeMarkerConfigurationFactoryBean cfg = new FreeMarkerConfigurationFactoryBean();
        cfg.setDefaultEncoding("UTF-8");
        return cfg;
    }
}
