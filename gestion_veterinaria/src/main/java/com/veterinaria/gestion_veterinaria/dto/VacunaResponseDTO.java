package com.veterinaria.gestion_veterinaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacunaResponseDTO {

    private Long idVacuna;
    private String nombreVacuna;
    private String descripcion;
    private LocalDate fechaAplicacion;
    private LocalDate proximaAplicacion;
    private Long idMascota;
    private String nombreMascota;
}