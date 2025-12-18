package com.lee.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("QR Code Generator API") // API 名称
                        .version("1.0.0")               // API 版本
                        .description("A professional API for generating QR codes and validating data.")); // 描述
    }
}
