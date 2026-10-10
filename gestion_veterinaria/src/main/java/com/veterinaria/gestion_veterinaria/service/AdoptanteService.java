package com.veterinaria.gestion_veterinaria.service;

import com.veterinaria.gestion_veterinaria.dto.AdoptanteRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.AdoptanteResponseDTO;
import com.veterinaria.gestion_veterinaria.exception.ResourceNotFoundException;
import com.veterinaria.gestion_veterinaria.mapper.AdoptanteMapper;
import com.veterinaria.gestion_veterinaria.model.Adoptante;
import com.veterinaria.gestion_veterinaria.repository.AdoptanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de Adoptantes.
 * Contiene la lógica de negocio para CRUD y borrado lógico.
 */
@Service
public class AdoptanteService {

    private final AdoptanteRepository adoptanteRepository;
    private final AdoptanteMapper adoptanteMapper;

    @Autowired
    public AdoptanteService(AdoptanteRepository adoptanteRepository,
                            AdoptanteMapper adoptanteMapper) {
        this.adoptanteRepository = adoptanteRepository;
        this.adoptanteMapper = adoptanteMapper;
    }

    /**
     * RF-01: Registrar adoptante.
     */
    public AdoptanteResponseDTO guardar(AdoptanteRequestDTO dto) {
        Adoptante adoptante = adoptanteMapper.toEntity(dto);
        Adoptante guardado = adoptanteRepository.save(adoptante);
        return adoptanteMapper.toResponseDTO(guardado);
    }

    /**
     * RF-02: Consultar adoptante por ID.
     */
    public AdoptanteResponseDTO buscarPorId(Long id) {
        Adoptante adoptante = adoptanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Adoptante no encontrado con ID: " + id));
        return adoptanteMapper.toResponseDTO(adoptante);
    }

    /**
     * RF-13: Listar únicamente registros activos.
     */
    public List<AdoptanteResponseDTO> listarActivos() {
        return adoptanteRepository.findByEstado(1)
                .stream()
                .map(adoptanteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * RF-03: Actualizar información de adoptantes.
     */
    public AdoptanteResponseDTO actualizar(Long id, AdoptanteRequestDTO dto) {
        Adoptante adoptante = adoptanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Adoptante no encontrado con ID: " + id));

        adoptanteMapper.updateEntity(adoptante, dto);
        Adoptante actualizado = adoptanteRepository.save(adoptante);
        return adoptanteMapper.toResponseDTO(actualizado);
    }

    /**
     * RF-04: Borrado lógico de adoptantes.
     * Cambia el estado de 1 (activo) a 0 (inactivo).
     */
    public void eliminarLogico(Long id) {
        Adoptante adoptante = adoptanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Adoptante no encontrado con ID: " + id));

        adoptante.setEstado(0);
        adoptanteRepository.save(adoptante);
    }
}