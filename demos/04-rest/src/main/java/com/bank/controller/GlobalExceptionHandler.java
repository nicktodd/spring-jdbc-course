package com.bank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

/**
 * GlobalExceptionHandler catches exceptions thrown by any controller and
 * converts them into consistent JSON error responses.
 *
 * @RestControllerAdvice applies this to all @RestController classes.
 * @ExceptionHandler(Type.class) marks a method as the handler for that exception.
 *
 * Without this class, Spring returns a generic HTML error page or a sparse JSON
 * response that is not useful to API clients.
 *
 * This is a preview of Module 05 content — shown here to make the demo
 * well-behaved when you test invalid inputs.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles business-rule violations (duplicate account, insufficient funds).
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of(
                "error", "Bad Request",
                "message", ex.getMessage(),
                "timestamp", Instant.now().toString()
        ));
    }
}
