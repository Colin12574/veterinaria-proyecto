package com.veterinaria.gestion_veterinaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private Long idAdoptante;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String rol;
}