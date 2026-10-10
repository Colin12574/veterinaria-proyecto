package com.veterinaria.gestion_veterinaria.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para recibir el correo en el login.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @Email(message = "El correo debe ser válido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;
}