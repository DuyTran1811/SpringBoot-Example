package com.example.auditinglogging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AuditingLoggingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuditingLoggingApplication.class, args);
    }

}
