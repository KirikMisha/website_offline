package com.example.test32;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.test32.repo")
@EntityScan(basePackages = "com.example.test32.models")
public class WorkWeb {

    public static void main(String[] args) {
        SpringApplication.run(WorkWeb.class, args);
    }
}
