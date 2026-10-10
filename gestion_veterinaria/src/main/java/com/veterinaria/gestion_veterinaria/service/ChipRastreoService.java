package com.veterinaria.gestion_veterinaria.service;

import com.veterinaria.gestion_veterinaria.dto.ChipRastreoRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.ChipRastreoResponseDTO;
import com.veterinaria.gestion_veterinaria.exception.ResourceNotFoundException;
import com.veterinaria.gestion_veterinaria.mapper.ChipRastreoMapper;
import com.veterinaria.gestion_veterinaria.model.ChipRastreo;
import com.veterinaria.gestion_veterinaria.model.Mascota;
import com.veterinaria.gestion_veterinaria.repository.ChipRastreoRepository;
import com.veterinaria.gestion_veterinaria.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de Chips de Rastreo.
 */
@Service
public class ChipRastreoService {

    private final ChipRastreoRepository chipRepository;
    private final MascotaRepository mascotaRepository;
    private final ChipRastreoMapper chipMapper;

    @Autowired
    public ChipRastreoService(ChipRastreoRepository chipRepository,
                              MascotaRepository mascotaRepository,
                              ChipRastreoMapper chipMapper) {
        this.chipRepository = chipRepository;
        this.mascotaRepository = mascotaRepository;
        this.chipMapper = chipMapper;
    }

    /**
     * RF-08: Registrar chip de rastreo para una mascota.
     */
    public ChipRastreoResponseDTO guardar(ChipRastreoRequestDTO dto) {
        Mascota mascota = mascotaRepository.findById(dto.getIdMascota())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Mascota no encontrada con ID: " + dto.getIdMascota()));

        if (mascota.getEstado() == 0) {
            throw new IllegalStateException(
                    "No se puede asignar un chip a una mascota inactiva");
        }

        // Validar que el código del chip no esté duplicado
        if (chipRepository.findByCodigoChip(dto.getCodigoChip()).isPresent()) {
            throw new IllegalStateException(
                    "Ya existe un chip con el código: " + dto.getCodigoChip());
        }

        ChipRastreo chip = chipMapper.toEntity(dto);
        chip.setMascota(mascota);

        ChipRastreo guardado = chipRepository.save(chip);
        return chipMapper.toResponseDTO(guardado);
    }

    public ChipRastreoResponseDTO buscarPorId(Long id) {
        ChipRastreo chip = chipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Chip no encontrado con ID: " + id));
        return chipMapper.toResponseDTO(chip);
    }

    public List<ChipRastreoResponseDTO> listarActivos() {
        return chipRepository.findByEstado(1)
                .stream()
                .map(chipMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void eliminarLogico(Long id) {
        ChipRastreo chip = chipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Chip no encontrado con ID: " + id));
        chip.setEstado(0);
        chipRepository.save(chip);
    }
}