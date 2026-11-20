package com.mudaeng.trgetter.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "TestAPI",
                description = "Description",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {
}
