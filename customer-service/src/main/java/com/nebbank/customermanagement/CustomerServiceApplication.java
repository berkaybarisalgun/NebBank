package com.nebbank.customermanagement;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info =@Info(
				title = "Customer Management microservice REST API Documentation",
				description = "NebBank Customer Management microservice REST API documentation",
				version = "v1",
				contact =@Contact(
						name = "Baris Algun",
						email = "info@barisalgun.dev",
						url = "https://www.barisalgun.dev"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://barisalgun.dev"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "NebBank Customer microservice REST API documentatation",
				url = "https://www.barisalgun.dev"
		)


)
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

}
