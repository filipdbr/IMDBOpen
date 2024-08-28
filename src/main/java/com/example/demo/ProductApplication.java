package com.example.demo;

import Utilities.DatabaseInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo", "Utilities"}) // Ensure Utilities package is scanned
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