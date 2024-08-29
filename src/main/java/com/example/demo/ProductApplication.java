package com.example.demo;

import Utilities.DatabaseInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"Entities.Business"}) // Add this annotation
@EnableJpaRepositories(basePackages = "Persistence.Repository") // Explicitly include the repository package
@ComponentScan(basePackages = {"Entities.Business", "Utilities", "Persistence.Repository", "Service"}) // Ensure all relevant packages are scanned
public class ProductApplication implements CommandLineRunner {

    @Autowired
    private DatabaseInitializer databaseInitializer;

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        databaseInitializer.createDatabase();
    }
}