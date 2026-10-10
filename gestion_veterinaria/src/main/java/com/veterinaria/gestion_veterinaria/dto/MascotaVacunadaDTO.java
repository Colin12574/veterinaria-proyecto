package com.veterinaria.gestion_veterinaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para el reporte consolidado de mascotas vacunadas.
 * Representa una mascota con su total de vacunas y la lista de nombres.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaVacunadaDTO {

    /** ID de la mascota. */
    private Long idMascota;

    /** Nombre de la mascota. */
    private String nombreMascota;

    /** Especie de la mascota (Perro, Gato, etc.). */
    private String especie;

    /** Total de vacunas aplicadas a la mascota. */
    private Long totalVacunas;

    /** Lista con los nombres de las vacunas aplicadas. */
    private List<String> vacunas;
}