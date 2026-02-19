package com.mukunthan.nefra_connections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point for the NEFRA Connections backend application.
 * This annotation enables auto-configuration, component scanning,
 * and allowed this class to define extra configuration.
 */
@SpringBootApplication
public class NefraConnectionsApplication {

    public static void main(String[] args) {
        // Launches the Spring application context and embedded Tomcat server
        SpringApplication.run(NefraConnectionsApplication.class, args);
    }
}