package com.maincrafts.contactapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Runs once on startup. Creates a default ADMIN account in the database if
// one doesn't already exist, since Task 6 moved away from Task 4's
// hardcoded in-memory user - everyone now lives in the "app_users" table.
@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User("admin", passwordEncoder.encode("admin@123"), "ADMIN");
            userRepository.save(admin);
            System.out.println("Seeded default ADMIN user -> username: admin | password: admin@123");
        }
    }
}
