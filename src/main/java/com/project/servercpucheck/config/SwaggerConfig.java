package com.project.servercpucheck.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CPU 사용률 수집 데이터")
                        .description("CPU 사용률을 기간별로 조회할 수 있는 API")
                        .version("1.0"));
    }
}
