package com.veterinaria.gestion_veterinaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para enviar datos de un Adoptante en las respuestas (GET).
 * Incluye el ID generado, el rol y excluye campos internos como 'estado'.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptanteResponseDTO {

    private Long idAdoptante;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String direccion;
    private String rol;
}