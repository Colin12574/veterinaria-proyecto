package com.veterinaria.gestion_veterinaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChipRastreoResponseDTO {

    private Long idChip;
    private String codigoChip;
    private LocalDate fechaImplantacion;
    private String fabricante;
    private Long idMascota;
    private String nombreMascota;
}