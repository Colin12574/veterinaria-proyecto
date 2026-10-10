package com.veterinaria.gestion_veterinaria.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador Global de Excepciones.
 * Captura todas las excepciones de la aplicación y las convierte en
 * respuestas JSON limpias y consistentes.
 * RNF-04: Manejo global de excepciones.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja la excepción ResourceNotFoundException (404 NOT FOUND).
     * Se lanza cuando un recurso no existe en la BD.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarResourceNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja excepciones de validación (400 BAD REQUEST).
     * Se lanza cuando los datos del DTO no cumplen las validaciones.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidaciones(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        // Recopilar todos los errores de validación
        Map<String, String> errores = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errores.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        // Construir la respuesta
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now());
        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("error", "VALIDATION_ERROR");
        respuesta.put("mensaje", "Error de validación en los datos enviados");
        respuesta.put("errores", errores);
        respuesta.put("path", request.getRequestURI());

        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja IllegalStateException (409 CONFLICT).
     * Se lanza cuando hay un conflicto de lógica de negocio.
     * Ejemplo: adoptante inactivo, mascota sin vacunas, etc.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> manejarIllegalState(
            IllegalStateException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "CONFLICT",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /**
     * Maneja cualquier otra excepción no capturada (500 INTERNAL SERVER ERROR).
     * Es el "catch-all" para errores inesperados.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGlobal(
            Exception ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                "Ocurrió un error inesperado: " + ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}