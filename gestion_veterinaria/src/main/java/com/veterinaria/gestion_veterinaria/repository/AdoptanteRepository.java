package com.veterinaria.gestion_veterinaria.repository;

import com.veterinaria.gestion_veterinaria.model.Adoptante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Adoptante.
 * Proporciona métodos CRUD y consultas personalizadas.
 */
@Repository
public interface AdoptanteRepository extends JpaRepository<Adoptante, Long> {

    /**
     * Lista todos los adoptantes activos (estado = 1).
     * RF-13: Listar únicamente registros activos.
     */
    List<Adoptante> findByEstado(Integer estado);

    /**
     * Busca un adoptante por su correo electrónico.
     */
    Optional<Adoptante> findByCorreo(String correo);

    /**
     * Verifica si existe un adoptante con ese correo.
     */
    boolean existsByCorreo(String correo);
}