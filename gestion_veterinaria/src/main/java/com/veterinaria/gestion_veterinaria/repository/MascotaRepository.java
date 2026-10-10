package com.veterinaria.gestion_veterinaria.repository;

import com.veterinaria.gestion_veterinaria.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;   // ← NUEVO IMPORT
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Mascota.
 * Proporciona métodos para consultar mascotas.
 */
@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    /**
     * Lista todas las mascotas activas (estado = 1).
     * RF-13: Listar únicamente registros activos.
     */
    List<Mascota> findByEstado(Integer estado);

    /**
     * Lista todas las mascotas de un adoptante específico.
     * RF-11: Consultar todas las mascotas asociadas a un adoptante.
     */
    List<Mascota> findByAdoptante_IdAdoptanteAndEstado(Long idAdoptante, Integer estado);

    /**
     * Lista mascotas por especie.
     */
    List<Mascota> findByEspecieAndEstado(String especie, Integer estado);

    /**
     * Consulta consolidada: trae mascotas activas con sus vacunas activas.
     * Se usa en el reporte de mascotas vacunadas.
     * ← NUEVO MÉTODO
     */
    @Query("SELECT DISTINCT m FROM Mascota m " +
            "JOIN FETCH m.vacunas v " +
            "WHERE m.estado = 1 AND v.estado = 1")
    List<Mascota> findMascotasConVacunasActivas();
}