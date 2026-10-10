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
public class ChipRastreoRequestDTO {

    @NotBlank(message = "El código del chip es obligatorio")
    @Size(max = 50, message = "El código no puede superar los 50 caracteres")
    private String codigoChip;

    @NotNull(message = "La fecha de implantación es obligatoria")
    private LocalDate fechaImplantacion;

    @Size(max = 100, message = "El fabricante no puede superar los 100 caracteres")
    private String fabricante;

    @NotNull(message = "El ID de la mascota es obligatorio")
    private Long idMascota;
}