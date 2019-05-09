package com.raziman.petronas;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@PropertySource(ignoreResourceNotFound = true, value = "classpath:resources/application.properties")
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PetronasApplication.class);
	}

}
