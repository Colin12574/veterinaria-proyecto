package com.veterinaria.gestion_veterinaria.repository;

import com.veterinaria.gestion_veterinaria.model.Vacuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Vacuna.
 * Proporciona métodos para consultar el historial de vacunación.
 */
@Repository
public interface VacunaRepository extends JpaRepository<Vacuna, Long> {

    /**
     * Lista todas las vacunas activas (estado = 1).
     */
    List<Vacuna> findByEstado(Integer estado);

    /**
     * Lista el historial de vacunas de una mascota específica.
     * RF-12: Consultar el historial de vacunas de una mascota.
     */
    List<Vacuna> findByMascota_IdMascotaAndEstado(Long idMascota, Integer estado);
}