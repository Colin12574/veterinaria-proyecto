package com.veterinaria.gestion_veterinaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;   // ← NUEVO IMPORT

/**
 * Entidad que representa a una Mascota en el sistema.
 * Una mascota puede ser adoptada por un Adoptante.
 */
@Entity
@Table(name = "MASCOTA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mascota {

    /** Identificador único de la mascota (clave primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MASCOTA")
    private Long idMascota;

    /** Nombre de la mascota. Obligatorio, máximo 100 caracteres. */
    @NotBlank(message = "El nombre de la mascota es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    /** Especie de la mascota (Perro, Gato, etc.). Máximo 50 caracteres. */
    @Size(max = 50, message = "La especie no puede superar los 50 caracteres")
    @Column(name = "ESPECIE", length = 50)
    private String especie;

    /** Raza de la mascota. Máximo 50 caracteres. */
    @Size(max = 50, message = "La raza no puede superar los 50 caracteres")
    @Column(name = "RAZA", length = 50)
    private String raza;

    /** Edad de la mascota en años. Debe ser 0 o positivo. */
    @PositiveOrZero(message = "La edad no puede ser negativa")
    @Column(name = "EDAD")
    private Integer edad;

    /** Sexo de la mascota (Macho/Hembra). Máximo 20 caracteres. */
    @Size(max = 20, message = "El sexo no puede superar los 20 caracteres")
    @Column(name = "SEXO", length = 20)
    private String sexo;

    /** Fecha en que la mascota ingresó al refugio. */
    @Column(name = "FECHA_INGRESO")
    private LocalDate fechaIngreso;

    /** Fecha en que la mascota fue adoptada. */
    @Column(name = "FECHA_ADOPCION")
    private LocalDate fechaAdopcion;

    /**
     * Adoptante que adoptó a esta mascota.
     * Relación @ManyToOne: muchas mascotas pertenecen a un adoptante.
     * La columna ID_ADOPTANTE es la clave foránea.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ADOPTANTE")
    private Adoptante adoptante;

    /**
     * Estado del registro para borrado lógico.
     * 1 = Activo, 0 = Inactivo.
     */
    @Column(name = "ESTADO", nullable = false)
    private Integer estado = 1;

    /**
     * Lista de vacunas aplicadas a esta mascota.
     * Relación @OneToMany: una mascota tiene muchas vacunas.
     * ← NUEVO CAMPO
     */
    @OneToMany(mappedBy = "mascota", fetch = FetchType.LAZY)
    private List<Vacuna> vacunas;
}