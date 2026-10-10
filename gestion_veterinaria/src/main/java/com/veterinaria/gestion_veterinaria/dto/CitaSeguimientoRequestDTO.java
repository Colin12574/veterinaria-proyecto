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
public class CitaSeguimientoRequestDTO {

    @NotNull(message = "La fecha de la cita es obligatoria")
    private LocalDate fechaCita;

    @NotBlank(message = "El motivo de la cita es obligatorio")
    @Size(max = 200, message = "El motivo no puede superar los 200 caracteres")
    private String motivo;

    @Size(max = 500, message = "Las observaciones no pueden superar los 500 caracteres")
    private String observaciones;

    @NotNull(message = "El ID de la mascota es obligatorio")
    private Long idMascota;
}