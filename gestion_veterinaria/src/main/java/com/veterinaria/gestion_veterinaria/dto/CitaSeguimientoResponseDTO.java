package com.veterinaria.gestion_veterinaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaSeguimientoResponseDTO {

    private Long idCita;
    private LocalDate fechaCita;
    private String motivo;
    private String observaciones;
    private String estatus;
    private Long idMascota;
    private String nombreMascota;
}