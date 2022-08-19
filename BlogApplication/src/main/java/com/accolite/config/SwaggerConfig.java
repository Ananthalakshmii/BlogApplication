package com.accolite.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {  //http://localhost:9090/swagger-ui/index.html
	
	public static final String AUTHORIZATION_HEADER="Authorization";
	
	@Bean
	public Docket api() { //to customize swagger url
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getApiInfo())
				.securityContexts(securityContexts())//to secure swagger with JWT
				.securitySchemes(Arrays.asList(apiKeys())) //to secure swagger with jwt and execute all commands thru swagger itself
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
		
	}

		private ApiKey apiKeys() {
			return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
		}

		private List<SecurityContext> securityContexts() {
			SecurityContext context=SecurityContext.builder().securityReferences(securityRef()).build();
			return Arrays.asList(context);
		}

	private List<SecurityReference> securityRef() {
			AuthorizationScope scope=new AuthorizationScope("global", "access everything");
			SecurityReference sref=new SecurityReference("JWT", new AuthorizationScope[] {scope});
			return Arrays.asList(sref);
		}

	private ApiInfo getApiInfo() {
		return new ApiInfo("Blog Application", "backedn application for blogging", 
				"1.0", 
				"Terms of service", 
				new Contact("Anantha lakshmi", "https://www.google.com", "mvananthu@gmail.com"), 
				"License of APIs", 
				"API License URL", 
				Collections.emptyList());
				
	}

}
