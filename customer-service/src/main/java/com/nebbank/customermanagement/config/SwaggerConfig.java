package com.nebbank.customermanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Customer Management Api")
                        .version("1.0")
                        .description("This API allows for managing customer data including creating, updating, fetching, and deleting customer records.")
                        .termsOfService("localhost:8080/termsofservice")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")
                        )
                        .contact(new Contact()
                                .email("info@barisalgun.dev")
                                .name("Berkay Baris Algun")
                                .url("https://barisalgun.dev")
                        )
                );
    }
}
