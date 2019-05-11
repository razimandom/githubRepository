package com.raziman.petronas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.raziman.petronas.properties.FileStorageProperties;

@SpringBootApplication
@EnableWebSecurity
@EnableConfigurationProperties({
	FileStorageProperties.class
})
public class PetronasApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(PetronasApplication.class, args);
	}

}
