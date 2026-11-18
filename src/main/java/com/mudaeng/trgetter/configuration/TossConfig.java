package com.mudaeng.trgetter.configuration;

import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.mudaeng")
public class TossConfig {
    public static String key = "";

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            if (template.url().contains("/oauth2/token")) {
                return;
            }
            template.header("authorization", "Bearer " + key);
            template.header("accept", "application/json");
        };
    }
}
