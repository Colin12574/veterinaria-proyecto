package com.veterinaria.gestion_veterinaria.exception;

/**
 * Excepción personalizada que se lanza cuando no se encuentra un recurso.
 * Será manejada por el GlobalExceptionHandler.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}