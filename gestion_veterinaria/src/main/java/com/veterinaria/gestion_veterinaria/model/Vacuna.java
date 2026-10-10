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
 * Entidad que representa una Vacuna aplicada a una Mascota.
 * Registra el historial médico de vacunación.
 */
@Entity
@Table(name = "VACUNA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vacuna {

    /** Identificador único de la vacuna (clave primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VACUNA")
    private Long idVacuna;

    /** Nombre de la vacuna (Rabia, Parvovirus, etc.). Obligatorio. */
    @NotBlank(message = "El nombre de la vacuna es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Column(name = "NOMBRE_VACUNA", nullable = false, length = 100)
    private String nombreVacuna;

    /** Descripción o notas adicionales de la vacuna. */
    @Size(max = 200, message = "La descripción no puede superar los 200 caracteres")
    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    /** Fecha en que se aplicó la vacuna. Obligatoria. */
    @NotNull(message = "La fecha de aplicación es obligatoria")
    @Column(name = "FECHA_APLICACION", nullable = false)
    private LocalDate fechaAplicacion;

    /**
     * Próxima fecha de aplicación.
     * Se calcula automáticamente (normalmente 1 año después).
     */
    @Column(name = "PROXIMA_APLICACION")
    private LocalDate proximaAplicacion;

    /**
     * Mascota a la que se le aplicó la vacuna.
     * Relación @ManyToOne: muchas vacunas pertenecen a una mascota.
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