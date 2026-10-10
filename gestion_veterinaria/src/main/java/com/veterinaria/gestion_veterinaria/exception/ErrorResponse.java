package com.veterinaria.gestion_veterinaria.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para estructurar las respuestas de error de la API.
 * Se usa en el GlobalExceptionHandler.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    /** Fecha y hora en que ocurrió el error. */
    private LocalDateTime timestamp;

    /** Código HTTP del error (404, 400, 500, etc.). */
    private int status;

    /** Nombre del error (NOT_FOUND, BAD_REQUEST, etc.). */
    private String error;

    /** Mensaje descriptivo del error. */
    private String mensaje;

    /** Ruta del endpoint donde ocurrió el error. */
    private String path;
}