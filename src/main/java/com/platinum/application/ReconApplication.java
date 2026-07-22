package com.platinum.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.platinum.repository"})
@EntityScan(basePackages = {"com.platinum.model"})
@ComponentScan(basePackages = {"com.platinum.application", "com.platinum.controller","com.platinum.model","com.platinum.service"})
public class ReconApplication {

	//private final DataLoaderTransaction dataLoaderTransaction;	
	public static void main(String[] args) {
		System.out.println("ReconApplication- infront");
		SpringApplication.run(ReconApplication.class, args);
		System.out.println("ReconApplication-After");
		 
	}
	
}  

