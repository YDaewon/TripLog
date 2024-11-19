package com.triplog.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

//Swagger-UI 확인
//http://localhost/swagger-ui.html

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI openAPI() {
		Info info = new Info().title("SSAFY Trip API 명세서").description(
				"<h3>SSAFY API Reference for Developers</h3>Swagger를 이용한 Trip API<br><img src=\"/assets/img/ssafy_logo.png\" width=\"150\">")
				.version("v1").contact(new io.swagger.v3.oas.models.info.Contact().name("ssafy")
						.email("qkqh9860@naver.com").url("http://edu.ssafy.com"));

		return new OpenAPI().components(new Components()
                .addSecuritySchemes("bearerAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("accessToken")))
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth")).info(info);
	}

	@Bean
	public GroupedOpenApi userApi() {
		return GroupedOpenApi.builder().group("ssafy-user").pathsToMatch("/user/**").build();
	}


}