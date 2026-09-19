package com.petadoption;

import com.petadoption.entity.User;
import com.petadoption.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PetAdoptionPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetAdoptionPortalApplication.class, args);
    }

    /**
     * Runs once at startup: creates the default admin account
     * (admin@petadoption.com / admin123) if it doesn't already exist,
     * so there's always a way to log in as Admin on a fresh database.
     */
    @Bean
    CommandLineRunner seedAdmin(UserRepository userRepository) {
        return args -> {
            if (!userRepository.existsByEmail("admin@petadoption.com")) {
                User admin = new User();
                admin.setFullName("System Admin");
                admin.setEmail("admin@petadoption.com");
                admin.setPassword("admin123"); // plain text, kept simple for this project
                admin.setPhone("0000000000");
                admin.setRole("ADMIN");
                userRepository.save(admin);
                System.out.println("Default admin created: admin@petadoption.com / admin123");
            }
        };
    }
}
