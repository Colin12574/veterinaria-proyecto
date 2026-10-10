package com.veterinaria.gestion_veterinaria.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para recibir datos de un Adoptante en las peticiones (POST, PUT).
 * Solo contiene los campos que el cliente puede enviar.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptanteRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @Size(max = 100, message = "El apellido no puede superar los 100 caracteres")
    private String apellido;

    @Size(max = 20, message = "El teléfono no puede superar los 20 caracteres")
    private String telefono;

    @Email(message = "El correo debe tener un formato válido")
    @Size(max = 150, message = "El correo no puede superar los 150 caracteres")
    private String correo;

    @Size(max = 200, message = "La dirección no puede superar los 200 caracteres")
    private String direccion;
}