package com.veterinaria.gestion_veterinaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa a un Adoptante en el sistema.
 * También se usa para login (rol ADMIN o CLIENTE).
 */
@Entity
@Table(name = "ADOPTANTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adoptante {

    /** Identificador único del adoptante (clave primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ADOPTANTE")
    private Long idAdoptante;

    /** Nombre del adoptante. Obligatorio. */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    /** Apellido del adoptante. */
    @Size(max = 100, message = "El apellido no puede superar los 100 caracteres")
    @Column(name = "APELLIDO", length = 100)
    private String apellido;

    /** Teléfono de contacto. */
    @Size(max = 20, message = "El teléfono no puede superar los 20 caracteres")
    @Column(name = "TELEFONO", length = 20)
    private String telefono;

    /** Correo electrónico. */
    @Email(message = "El correo debe tener un formato válido")
    @Size(max = 150, message = "El correo no puede superar los 150 caracteres")
    @Column(name = "CORREO", length = 150)
    private String correo;

    /** Dirección del adoptante. */
    @Size(max = 200, message = "La dirección no puede superar los 200 caracteres")
    @Column(name = "DIRECCION", length = 200)
    private String direccion;

    /** Rol del usuario: ADMIN o CLIENTE. */
    @Size(max = 20)
    @Column(name = "ROL", length = 20)
    private String rol = "CLIENTE";

    /** Estado del registro para borrado lógico. 1=Activo, 0=Inactivo. */
    @Column(name = "ESTADO", nullable = false)
    private Integer estado = 1;
}