package com.veterinaria.gestion_veterinaria.mapper;

import com.veterinaria.gestion_veterinaria.dto.CitaSeguimientoRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.CitaSeguimientoResponseDTO;
import com.veterinaria.gestion_veterinaria.model.CitaSeguimiento;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre CitaSeguimiento (Entidad) y sus DTOs.
 */
@Component
public class CitaSeguimientoMapper {

    public CitaSeguimiento toEntity(CitaSeguimientoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        CitaSeguimiento cita = new CitaSeguimiento();
        cita.setFechaCita(dto.getFechaCita());
        cita.setMotivo(dto.getMotivo());
        cita.setObservaciones(dto.getObservaciones());
        cita.setEstatus("PENDIENTE"); // Estatus inicial
        cita.setEstado(1);
        return cita;
    }

    public CitaSeguimientoResponseDTO toResponseDTO(CitaSeguimiento entity) {
        if (entity == null) {
            return null;
        }
        CitaSeguimientoResponseDTO dto = new CitaSeguimientoResponseDTO();
        dto.setIdCita(entity.getIdCita());
        dto.setFechaCita(entity.getFechaCita());
        dto.setMotivo(entity.getMotivo());
        dto.setObservaciones(entity.getObservaciones());
        dto.setEstatus(entity.getEstatus());

        if (entity.getMascota() != null) {
            dto.setIdMascota(entity.getMascota().getIdMascota());
            dto.setNombreMascota(entity.getMascota().getNombre());
        }
        return dto;
    }

    public void updateEntity(CitaSeguimiento entity, CitaSeguimientoRequestDTO dto) {
        if (entity == null || dto == null) {
            return;
        }
        entity.setFechaCita(dto.getFechaCita());
        entity.setMotivo(dto.getMotivo());
        entity.setObservaciones(dto.getObservaciones());
    }
}