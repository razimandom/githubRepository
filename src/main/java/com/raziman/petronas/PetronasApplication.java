package com.raziman.petronas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication(scanBasePackages = "com.raziman.petronas")
public class PetronasApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetronasApplication.class, args);
	}

}
