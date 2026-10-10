package com.veterinaria.gestion_veterinaria.repository;

import com.veterinaria.gestion_veterinaria.model.ChipRastreo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad ChipRastreo.
 */
@Repository
public interface ChipRastreoRepository extends JpaRepository<ChipRastreo, Long> {

    /**
     * Lista todos los chips activos (estado = 1).
     */
    List<ChipRastreo> findByEstado(Integer estado);

    /**
     * Busca un chip por su código único.
     */
    Optional<ChipRastreo> findByCodigoChip(String codigoChip);

    /**
     * Lista los chips de una mascota específica.
     */
    List<ChipRastreo> findByMascota_IdMascotaAndEstado(Long idMascota, Integer estado);
}