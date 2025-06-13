package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler to manage application-wide exceptions.
 *
 * ✅ Responsibilities:
 * - Handles exceptions thrown across the entire application.
 * - Returns structured error responses.
 * - Prevents stack traces from being exposed to the client.
 */
@RestControllerAdvice // Enables global exception handling
public class GlobalExceptionHandler {

    /**
     * Handles all RuntimeExceptions and returns a structured error response.
     *
     * @param ex The caught RuntimeException.
     * @return ResponseEntity containing an error message and status code.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("error", "Internal Server Error");
        errorResponse.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Handles all generic exceptions and returns a structured error response.
     *
     * @param ex The caught Exception.
     * @return ResponseEntity containing an error message and status code.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("error", "Unexpected Error");
        errorResponse.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}