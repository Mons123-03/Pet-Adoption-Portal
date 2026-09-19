package com.petadoption.config;

import com.petadoption.service.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Catches ApiException thrown anywhere in the service layer and turns it
 * into a simple {"error": "..."} JSON response with the right HTTP status,
 * so individual controller methods don't need try/catch blocks everywhere.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, String>> handleApiException(ApiException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "Something went wrong: " + ex.getMessage());
        return ResponseEntity.status(500).body(body);
    }
}
