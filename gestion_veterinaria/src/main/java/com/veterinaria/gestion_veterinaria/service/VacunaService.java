package com.veterinaria.gestion_veterinaria.service;

import com.veterinaria.gestion_veterinaria.dto.VacunaRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.VacunaResponseDTO;
import com.veterinaria.gestion_veterinaria.exception.ResourceNotFoundException;
import com.veterinaria.gestion_veterinaria.mapper.VacunaMapper;
import com.veterinaria.gestion_veterinaria.model.Mascota;
import com.veterinaria.gestion_veterinaria.model.Vacuna;
import com.veterinaria.gestion_veterinaria.repository.MascotaRepository;
import com.veterinaria.gestion_veterinaria.repository.VacunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacunaService {

    private final VacunaRepository vacunaRepository;
    private final MascotaRepository mascotaRepository;
    private final VacunaMapper vacunaMapper;

    @Autowired
    public VacunaService(VacunaRepository vacunaRepository,
                         MascotaRepository mascotaRepository,
                         VacunaMapper vacunaMapper) {
        this.vacunaRepository = vacunaRepository;
        this.mascotaRepository = mascotaRepository;
        this.vacunaMapper = vacunaMapper;
    }

    public VacunaResponseDTO guardar(VacunaRequestDTO dto) {
        Mascota mascota = mascotaRepository.findById(dto.getIdMascota())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Mascota no encontrada con ID: " + dto.getIdMascota()));

        if (mascota.getEstado() == 0) {
            throw new IllegalStateException(
                    "No se puede vacunar a una mascota inactiva");
        }

        Vacuna vacuna = vacunaMapper.toEntity(dto);
        vacuna.setMascota(mascota);
        vacuna.setProximaAplicacion(dto.getFechaAplicacion().plusYears(1));

        Vacuna guardada = vacunaRepository.save(vacuna);
        return vacunaMapper.toResponseDTO(guardada);
    }

    public VacunaResponseDTO buscarPorId(Long id) {
        Vacuna vacuna = vacunaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vacuna no encontrada con ID: " + id));
        return vacunaMapper.toResponseDTO(vacuna);
    }

    public List<VacunaResponseDTO> listarActivas() {
        return vacunaRepository.findByEstado(1)
                .stream()
                .map(vacunaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void eliminarLogico(Long id) {
        Vacuna vacuna = vacunaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vacuna no encontrada con ID: " + id));
        vacuna.setEstado(0);
        vacunaRepository.save(vacuna);
    }

    public List<VacunaResponseDTO> listarPorMascota(Long idMascota) {
        if (!mascotaRepository.existsById(idMascota)) {
            throw new ResourceNotFoundException(
                    "Mascota no encontrada con ID: " + idMascota);
        }

        return vacunaRepository.findByMascota_IdMascotaAndEstado(idMascota, 1)
                .stream()
                .map(vacunaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ============================================================
    // NUEVO MÉTODO: Listar vacunas de TODAS las mascotas del adoptante
    // ============================================================
    public List<VacunaResponseDTO> listarPorAdoptante(Long idAdoptante) {
        // 1. Buscamos todas las mascotas del adoptante
        List<Mascota> mascotas = mascotaRepository.findAll()
                .stream()
                .filter(m -> m.getAdoptante() != null
                        && m.getAdoptante().getIdAdoptante().equals(idAdoptante))
                .collect(Collectors.toList());

        // 2. Obtenemos las vacunas de cada mascota
        return mascotas.stream()
                .flatMap(m -> vacunaRepository.findByMascota_IdMascotaAndEstado(
                        m.getIdMascota(), 1).stream())
                .map(vacunaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}