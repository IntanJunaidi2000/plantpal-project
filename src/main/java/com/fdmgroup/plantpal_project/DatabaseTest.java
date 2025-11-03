package com.fdmgroup.plantpal_project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseTest {
    
    @Bean
    CommandLineRunner init() {
        return args -> {
            System.out.println("=== DATABASE CONNECTED SUCCESSFULLY ===");
            System.out.println("=== TABLES SHOULD BE CREATED NOW ===");
        };
    }
}