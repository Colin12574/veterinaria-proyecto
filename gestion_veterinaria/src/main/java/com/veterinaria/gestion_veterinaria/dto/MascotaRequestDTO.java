package com.veterinaria.gestion_veterinaria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para recibir datos de una Mascota en las peticiones (POST, PUT).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaRequestDTO {

    @NotBlank(message = "El nombre de la mascota es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @Size(max = 50, message = "La especie no puede superar los 50 caracteres")
    private String especie;

    @Size(max = 50, message = "La raza no puede superar los 50 caracteres")
    private String raza;

    @PositiveOrZero(message = "La edad no puede ser negativa")
    private Integer edad;

    @Size(max = 20, message = "El sexo no puede superar los 20 caracteres")
    private String sexo;

    private LocalDate fechaIngreso;
    private LocalDate fechaAdopcion;

    // ID del adoptante (solo el ID, no el objeto completo)
    private Long idAdoptante;
}