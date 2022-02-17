package ru.geekbrains.FrontService.config;

import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import ru.geekbrains.FrontService.feignClient.decoder.CustomErrorDecoder;

public class FeignClientConfig {

    @Bean
    @ConditionalOnMissingBean(value = ErrorDecoder.class)
    public CustomErrorDecoder decoder() {
        return new CustomErrorDecoder();
    }
}
