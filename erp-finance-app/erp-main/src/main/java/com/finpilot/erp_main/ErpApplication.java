package com.finpilot.erp_main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.finpilot")
@EntityScan(basePackages = "com.finpilot.erp_ar.entity")
@EnableJpaRepositories(basePackages = {
        "com.finpilot.erp_ar.repository",
})
public class ErpApplication {
    public static void main(String[] args) {
        SpringApplication.run(ErpApplication.class, args);
    }
}
