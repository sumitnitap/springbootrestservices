package com.foodaggregator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket FoodApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()                 .apis(RequestHandlerSelectors.basePackage("com.foodaggregator.controller"))
				.build()
				.apiInfo(metaData());
	}
	private ApiInfo metaData() {
		ApiInfo apiInfo = new ApiInfo(
				"Food Catalog from Vendor",
				"Food Aggregator Demo",
				"1.0",
				"Terms of service",
				new Contact("sumit thakur", "", "sumit.nitap@gmail.com"),
				"",
				"");
		return apiInfo;
	}
}
