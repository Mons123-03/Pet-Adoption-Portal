package com.petadoption.util;

import com.petadoption.service.ApiException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;

public class SessionUtil {

    public static Integer requireUserId(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Not logged in");
        }
        return userId;
    }

    public static void requireRole(HttpSession session, String role) {
        Integer userId = (Integer) session.getAttribute("userId");
        String sessionRole = (String) session.getAttribute("role");

        if (userId == null) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Not logged in");
        }
        if (sessionRole == null || !sessionRole.equals(role)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Access denied: " + role + " role required");
        }
    }
}
