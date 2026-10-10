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
 * Entidad que representa un Chip de Rastreo implantado en una Mascota.
 * Permite localizar a la mascota en caso de extravío.
 */
@Entity
@Table(name = "CHIP_RASTREO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChipRastreo {

    /** Identificador único del chip (clave primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CHIP")
    private Long idChip;

    /** Código único del chip (número de serie). Obligatorio, máximo 50 caracteres. */
    @NotBlank(message = "El código del chip es obligatorio")
    @Size(max = 50, message = "El código no puede superar los 50 caracteres")
    @Column(name = "CODIGO_CHIP", nullable = false, length = 50, unique = true)
    private String codigoChip;

    /** Fecha en que se implantó el chip. Obligatoria. */
    @NotNull(message = "La fecha de implantación es obligatoria")
    @Column(name = "FECHA_IMPLANTACION", nullable = false)
    private LocalDate fechaImplantacion;

    /** Fabricante del chip (PetTrack, GPSMax, etc.). Máximo 100 caracteres. */
    @Size(max = 100, message = "El fabricante no puede superar los 100 caracteres")
    @Column(name = "FABRICANTE", length = 100)
    private String fabricante;

    /**
     * Mascota en la que se implantó el chip.
     * Relación @ManyToOne: muchos chips pueden pertenecer a una mascota.
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