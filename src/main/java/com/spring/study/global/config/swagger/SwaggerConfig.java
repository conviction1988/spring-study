package com.spring.study.global.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@Configuration
@SecurityScheme(
		name = "Bearer Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi v1API() {
		return GroupedOpenApi.builder()
				.group("v1")
				.pathsToMatch("/api/v1/**")
				.build();
	}

	@Bean
	public GroupedOpenApi v2API() {
		return GroupedOpenApi.builder()
				.group("v2")
				.pathsToMatch("/api/v2/**")
				.build();
	}

	@Bean
	public OpenAPI customOpenAPI() {

		Contact contact = new Contact();
		contact.setUrl("http://localhost:8080/docs/index.html");

		return new OpenAPI()
				.components(new Components())
				.info(new Info().title("Spring Boot HTTP API")
						.description("API 명세 문서(Rest Docs)는 아래 URL 클릭")
						.contact(contact)
						.version("1.0.0"));
	}

}
