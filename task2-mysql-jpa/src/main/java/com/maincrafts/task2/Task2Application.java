package com.maincrafts.task2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Task2Application {
    public static void main(String[] args) {
        SpringApplication.run(Task2Application.class, args);
        System.out.println("Task 2 backend running at http://localhost:8080");
    }
}
