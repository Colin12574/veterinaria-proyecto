package com.veterinaria.gestion_veterinaria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacunaRequestDTO {

    @NotBlank(message = "El nombre de la vacuna es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombreVacuna;

    @Size(max = 200, message = "La descripción no puede superar los 200 caracteres")
    private String descripcion;

    @NotNull(message = "La fecha de aplicación es obligatoria")
    private LocalDate fechaAplicacion;

    @NotNull(message = "El ID de la mascota es obligatorio")
    private Long idMascota;
}