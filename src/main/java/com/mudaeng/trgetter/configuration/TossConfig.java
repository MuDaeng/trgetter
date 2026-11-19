package com.mudaeng.trgetter.configuration;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class TossConfig {
    public static String key = "";

    @Bean
    public RequestInterceptor tossRequestInterceptor() {
        return template -> {
            if (template.url().contains("/oauth2/token")) {
                return;
            }
            template.header("authorization", "Bearer " + key);
            template.header("accept", "application/json");
        };
    }
}
