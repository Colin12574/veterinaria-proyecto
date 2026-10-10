package com.veterinaria.gestion_veterinaria.service;

import com.veterinaria.gestion_veterinaria.dto.MascotaRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.MascotaResponseDTO;
import com.veterinaria.gestion_veterinaria.exception.ResourceNotFoundException;
import com.veterinaria.gestion_veterinaria.mapper.MascotaMapper;
import com.veterinaria.gestion_veterinaria.model.Adoptante;
import com.veterinaria.gestion_veterinaria.model.Mascota;
import com.veterinaria.gestion_veterinaria.repository.AdoptanteRepository;
import com.veterinaria.gestion_veterinaria.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de Mascotas.
 * Contiene la lógica de negocio, incluyendo validación del adoptante.
 */
@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final AdoptanteRepository adoptanteRepository;
    private final MascotaMapper mascotaMapper;

    @Autowired
    public MascotaService(MascotaRepository mascotaRepository,
                          AdoptanteRepository adoptanteRepository,
                          MascotaMapper mascotaMapper) {
        this.mascotaRepository = mascotaRepository;
        this.adoptanteRepository = adoptanteRepository;
        this.mascotaMapper = mascotaMapper;
    }

    /**
     * RF-05: Registrar mascota.
     * ENDPOINT INTERMEDIO 1: Valida existencia del adoptante y que esté activo.
     */
    public MascotaResponseDTO guardar(MascotaRequestDTO dto) {
        // 1. Validar que el adoptante existe
        Adoptante adoptante = adoptanteRepository.findById(dto.getIdAdoptante())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Adoptante no encontrado con ID: " + dto.getIdAdoptante()));

        // 2. Verificar que el adoptante esté activo
        if (adoptante.getEstado() == 0) {
            throw new IllegalStateException(
                    "No se puede asociar una mascota a un adoptante inactivo");
        }

        // 3. Crear la mascota
        Mascota mascota = mascotaMapper.toEntity(dto);
        mascota.setAdoptante(adoptante);

        // 4. Registrar fecha de adopción (si no viene, usar la fecha actual)
        if (mascota.getFechaAdopcion() == null) {
            mascota.setFechaAdopcion(LocalDate.now());
        }

        // 5. Guardar
        Mascota guardada = mascotaRepository.save(mascota);
        return mascotaMapper.toResponseDTO(guardada);
    }

    public MascotaResponseDTO buscarPorId(Long id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Mascota no encontrada con ID: " + id));
        return mascotaMapper.toResponseDTO(mascota);
    }

    public List<MascotaResponseDTO> listarActivas() {
        return mascotaRepository.findByEstado(1)
                .stream()
                .map(mascotaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MascotaResponseDTO actualizar(Long id, MascotaRequestDTO dto) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Mascota no encontrada con ID: " + id));

        mascotaMapper.updateEntity(mascota, dto);
        Mascota actualizada = mascotaRepository.save(mascota);
        return mascotaMapper.toResponseDTO(actualizada);
    }

    public void eliminarLogico(Long id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Mascota no encontrada con ID: " + id));

        mascota.setEstado(0);
        mascotaRepository.save(mascota);
    }

    /**
     * RF-11: Consultar todas las mascotas asociadas a un adoptante.
     */
    public List<MascotaResponseDTO> listarPorAdoptante(Long idAdoptante) {
        // Validar que el adoptante exista
        if (!adoptanteRepository.existsById(idAdoptante)) {
            throw new ResourceNotFoundException(
                    "Adoptante no encontrado con ID: " + idAdoptante);
        }

        return mascotaRepository.findByAdoptante_IdAdoptanteAndEstado(idAdoptante, 1)
                .stream()
                .map(mascotaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}