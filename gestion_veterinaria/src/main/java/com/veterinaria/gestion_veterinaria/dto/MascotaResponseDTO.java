package com.veterinaria.gestion_veterinaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para enviar datos de una Mascota en las respuestas (GET).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaResponseDTO {

    private Long idMascota;
    private String nombre;
    private String especie;
    private String raza;
    private Integer edad;
    private String sexo;
    private LocalDate fechaIngreso;
    private LocalDate fechaAdopcion;

    // Datos del adoptante (sin exponer la entidad completa)
    private Long idAdoptante;
    private String nombreAdoptante;
}