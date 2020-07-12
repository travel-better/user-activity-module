package com.callforcode.travelbetter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
				.apiInfo(apiInfo()).select().paths(postPaths()).build();
	}

	private Predicate<String> postPaths() {
		return regex("/travelbetter.*");
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Travel Better API")
				.description("TravelBetter API reference for developers")
				.termsOfServiceUrl("https://callforcode.org/")
				.contact("preetiagarwal26@gmail.com").license("TravelBetter License")
				.licenseUrl("preetiagarwal26@gmail.com").version("1.0").build();
	}

}