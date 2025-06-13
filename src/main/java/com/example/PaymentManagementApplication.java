package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for the Payment Management Application.
 * This class serves as the starting point for the Spring Boot application.
 *
 * ✅ Responsibilities:
 * - Initializes the Spring framework
 * - Starts the embedded web server (Tomcat by default)
 * - Configures application components automatically
 */
@SpringBootApplication // Enables Spring Boot's auto-configuration, component scanning, and configuration support.
public class PaymentManagementApplication {

    /**
     * The main method that starts the Spring Boot application.
     * It launches the embedded web server and initializes the Spring context.
     *
     * @param args Command-line arguments (if any).
     */
    public static void main(String[] args) {
        SpringApplication.run(PaymentManagementApplication.class, args);
    }
}