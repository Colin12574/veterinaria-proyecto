package com.veterinaria.gestion_veterinaria.repository;

import com.veterinaria.gestion_veterinaria.model.CitaSeguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad CitaSeguimiento.
 */
@Repository
public interface CitaSeguimientoRepository extends JpaRepository<CitaSeguimiento, Long> {

    /**
     * Lista todas las citas activas (estado = 1).
     */
    List<CitaSeguimiento> findByEstado(Integer estado);

    /**
     * Lista las citas de una mascota específica.
     */
    List<CitaSeguimiento> findByMascota_IdMascotaAndEstado(Long idMascota, Integer estado);

    /**
     * Lista las citas por estatus (PENDIENTE, COMPLETADA, CANCELADA).
     */
    List<CitaSeguimiento> findByEstatusAndEstado(String estatus, Integer estado);
}