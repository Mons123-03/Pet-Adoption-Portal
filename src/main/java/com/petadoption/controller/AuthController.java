package com.petadoption.controller;

import com.petadoption.dto.UserDTO;
import com.petadoption.entity.User;
import com.petadoption.service.ApiException;
import com.petadoption.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Map<String, String> register(
            @RequestParam("full_name") String fullName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "role", required = false) String role) {

        userService.register(fullName, email, password, phone, role);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Registration successful");
        return response;
    }

    @PostMapping("/login")
    public Map<String, Object> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session) {

        User user = userService.login(email, password);

        session.setAttribute("userId", user.getId());
        session.setAttribute("fullName", user.getFullName());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("role", user.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("user", UserDTO.fromEntity(user));
        return response;
    }

    @PostMapping("/logout")
    public Map<String, String> logout(HttpSession session) {
        session.invalidate();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        return response;
    }

    @GetMapping("/me")
    public Map<String, Object> me(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Not logged in");
        }

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("user_id", userId);
        userMap.put("full_name", session.getAttribute("fullName"));
        userMap.put("email", session.getAttribute("email"));
        userMap.put("role", session.getAttribute("role"));

        Map<String, Object> response = new HashMap<>();
        response.put("user", userMap);
        return response;
    }
}
