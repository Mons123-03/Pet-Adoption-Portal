package com.petadoption.service;

import org.springframework.http.HttpStatus;

/**
 * A simple exception carrying an HTTP status code, thrown by service
 * methods when a request can't be fulfilled (not found, forbidden, etc).
 * Caught centrally in each controller's methods and turned into a
 * JSON error response.
 */
public class ApiException extends RuntimeException {

    private final HttpStatus status;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() { return status; }
}
