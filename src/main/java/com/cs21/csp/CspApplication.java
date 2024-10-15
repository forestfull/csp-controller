package com.cs21.csp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CspApplication {

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(CspApplication.class);



        application.run(args);
    }
}