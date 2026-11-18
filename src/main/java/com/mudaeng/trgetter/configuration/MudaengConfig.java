package com.mudaeng.trgetter.configuration;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.mudaeng")
public class MudaengConfig {
    @Value("${api.key.mudaeng.access-token}")
    private String accessToken;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            template.header("authorization", "Bearer " + accessToken);
            template.header("accept", "application/json");
        };
    }
}
