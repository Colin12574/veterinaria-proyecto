package com.veterinaria.gestion_veterinaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidad que representa una Cita de Seguimiento Veterinario.
 * Se programa para dar seguimiento a las mascotas adoptadas.
 */
@Entity
@Table(name = "CITA_SEGUIMIENTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaSeguimiento {

    /** Identificador único de la cita (clave primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CITA")
    private Long idCita;

    /** Fecha programada para la cita. Obligatoria. */
    @NotNull(message = "La fecha de la cita es obligatoria")
    @Column(name = "FECHA_CITA", nullable = false)
    private LocalDate fechaCita;

    /** Motivo de la cita (Revisión, Vacuna, Chequeo, etc.). Obligatorio. */
    @NotBlank(message = "El motivo de la cita es obligatorio")
    @Size(max = 200, message = "El motivo no puede superar los 200 caracteres")
    @Column(name = "MOTIVO", nullable = false, length = 200)
    private String motivo;

    /** Observaciones del veterinario. Máximo 500 caracteres. */
    @Size(max = 500, message = "Las observaciones no pueden superar los 500 caracteres")
    @Column(name = "OBSERVACIONES", length = 500)
    private String observaciones;

    /**
     * Estatus de la cita: PENDIENTE, COMPLETADA, CANCELADA.
     * Por defecto, una cita nueva está PENDIENTE.
     */
    @Size(max = 20, message = "El estatus no puede superar los 20 caracteres")
    @Column(name = "ESTATUS", length = 20)
    private String estatus = "PENDIENTE";

    /**
     * Mascota asociada a esta cita.
     * Relación @ManyToOne: muchas citas pueden pertenecer a una mascota.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MASCOTA", nullable = false)
    private Mascota mascota;

    /**
     * Estado del registro para borrado lógico.
     * 1 = Activo, 0 = Inactivo.
     */
    @Column(name = "ESTADO", nullable = false)
    private Integer estado = 1;
}