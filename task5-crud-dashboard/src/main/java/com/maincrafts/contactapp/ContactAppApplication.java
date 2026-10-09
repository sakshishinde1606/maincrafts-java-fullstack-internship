package com.maincrafts.contactapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContactAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContactAppApplication.class, args);
        System.out.println("Backend running at http://localhost:8080");
    }
}
