package com.schoolerp.mysaas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class SchoolErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolErpApplication.class, args);
    }

}
