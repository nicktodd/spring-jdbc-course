package com.stocks.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO 1: Add @RestControllerAdvice annotation.
 *
 * TODO 2: Add a Logger field:
 *   private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
 *
 * TODO 3: Implement handleBadRequest(IllegalArgumentException ex):
 *   - @ExceptionHandler(IllegalArgumentException.class)
 *   - Log at WARN: log.warn("Business rule violation: {}", ex.getMessage())
 *   - Return ResponseEntity.badRequest().body(Map.of("error", "Bad Request", "message", ex.getMessage(), "timestamp", ...))
 *
 * TODO 4: Implement handleValidationErrors(MethodArgumentNotValidException ex):
 *   - @ExceptionHandler(MethodArgumentNotValidException.class)
 *   - Collect field errors using ex.getBindingResult().getFieldErrors()
 *   - Map each FieldError to: fieldName → defaultMessage
 *   - Return ResponseEntity.badRequest() with body {"error":"Validation Failed","fields":{...},"timestamp":...}
 *
 * Hint: Look at the demo GlobalExceptionHandler for the full implementation.
 */
public class GlobalExceptionHandler {
    // TODO 1-4
}
