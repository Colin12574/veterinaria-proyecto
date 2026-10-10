package com.veterinaria.gestion_veterinaria.service;

import com.veterinaria.gestion_veterinaria.dto.CitaSeguimientoRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.CitaSeguimientoResponseDTO;
import com.veterinaria.gestion_veterinaria.exception.ResourceNotFoundException;
import com.veterinaria.gestion_veterinaria.mapper.CitaSeguimientoMapper;
import com.veterinaria.gestion_veterinaria.model.CitaSeguimiento;
import com.veterinaria.gestion_veterinaria.model.Mascota;
import com.veterinaria.gestion_veterinaria.repository.CitaSeguimientoRepository;
import com.veterinaria.gestion_veterinaria.repository.MascotaRepository;
import com.veterinaria.gestion_veterinaria.repository.VacunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaSeguimientoService {

    private final CitaSeguimientoRepository citaRepository;
    private final MascotaRepository mascotaRepository;
    private final VacunaRepository vacunaRepository;
    private final CitaSeguimientoMapper citaMapper;

    @Autowired
    public CitaSeguimientoService(CitaSeguimientoRepository citaRepository,
                                  MascotaRepository mascotaRepository,
                                  VacunaRepository vacunaRepository,
                                  CitaSeguimientoMapper citaMapper) {
        this.citaRepository = citaRepository;
        this.mascotaRepository = mascotaRepository;
        this.vacunaRepository = vacunaRepository;
        this.citaMapper = citaMapper;
    }

    public CitaSeguimientoResponseDTO guardar(CitaSeguimientoRequestDTO dto) {
        Mascota mascota = mascotaRepository.findById(dto.getIdMascota())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Mascota no encontrada con ID: " + dto.getIdMascota()));

        if (mascota.getEstado() == 0) {
            throw new IllegalStateException(
                    "No se puede programar una cita para una mascota inactiva");
        }

        if (mascota.getAdoptante() == null) {
            throw new IllegalStateException(
                    "La mascota no tiene adoptante asignado");
        }
        if (mascota.getAdoptante().getEstado() == 0) {
            throw new IllegalStateException(
                    "El adoptante de la mascota está inactivo");
        }

        if (vacunaRepository.findByMascota_IdMascotaAndEstado(
                mascota.getIdMascota(), 1).isEmpty()) {
            throw new IllegalStateException(
                    "La mascota no tiene esquema de vacunación registrado");
        }

        CitaSeguimiento cita = citaMapper.toEntity(dto);
        cita.setMascota(mascota);

        CitaSeguimiento guardada = citaRepository.save(cita);
        return citaMapper.toResponseDTO(guardada);
    }

    public CitaSeguimientoResponseDTO buscarPorId(Long id) {
        CitaSeguimiento cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada con ID: " + id));
        return citaMapper.toResponseDTO(cita);
    }

    public List<CitaSeguimientoResponseDTO> listarActivas() {
        return citaRepository.findByEstado(1)
                .stream()
                .map(citaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ============================================================
    // NUEVO MÉTODO: Listar citas SOLO del adoptante indicado
    // ============================================================
    public List<CitaSeguimientoResponseDTO> listarPorAdoptante(Long idAdoptante) {
        return citaRepository.findByEstado(1)
                .stream()
                .filter(c -> c.getMascota() != null
                        && c.getMascota().getAdoptante() != null
                        && c.getMascota().getAdoptante().getIdAdoptante().equals(idAdoptante))
                .map(citaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void cancelarCita(Long id) {
        CitaSeguimiento cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada con ID: " + id));

        cita.setEstatus("CANCELADA");
        cita.setEstado(0);
        citaRepository.save(cita);
    }
}