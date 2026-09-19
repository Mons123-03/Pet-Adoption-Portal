package com.petadoption.service;

import com.petadoption.entity.User;
import com.petadoption.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(String fullName, String email, String password, String phone, String role) {
        // Full Name Validation
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Full name is required");
        }

        if (!fullName.matches("^[A-Za-z ]+$")) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "Full name should contain only letters");
        }

// Email Validation
        if (email == null || email.trim().isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "Email is required");
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (!email.matches(emailRegex)) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "Enter a valid email");
        }

// Password Validation
        if (password == null || password.trim().isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "Password is required");
        }

        if (password.length() < 8) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "Password must be at least 8 characters");
        }

// Phone Validation
        if (phone == null || !phone.matches("\\d{10}")) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "Phone number must contain exactly 10 digits");
        }

        if (role == null || (!role.equals("ADOPTER") && !role.equals("OWNER"))) {
            role = "ADOPTER";
        }

        if (userRepository.existsByEmail(email)) {
            throw new ApiException(HttpStatus.CONFLICT, "Email already registered");
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password); // plain text, kept simple for this project
        user.setPhone(phone);
        user.setRole(role);

        return userRepository.save(user);
    }

    public User login(String email, String password) {

        if (email == null || email.trim().isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Email is required");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Password is required");
        }

        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED,
                        "Invalid email or password"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByOrderByCreatedAtDesc();
    }

    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));
        if ("ADMIN".equals(user.getRole())) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Cannot delete an admin account");
        }
        userRepository.delete(user);
    }

    public User getById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public long countByRole(String role) {
        return userRepository.countByRole(role);
    }

    public long countAll() {
        return userRepository.count();
    }
}
