package com.veterinaria.gestion_veterinaria.mapper;

import com.veterinaria.gestion_veterinaria.dto.VacunaRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.VacunaResponseDTO;
import com.veterinaria.gestion_veterinaria.model.Vacuna;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre Vacuna (Entidad) y sus DTOs.
 */
@Component
public class VacunaMapper {

    public Vacuna toEntity(VacunaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Vacuna vacuna = new Vacuna();
        vacuna.setNombreVacuna(dto.getNombreVacuna());
        vacuna.setDescripcion(dto.getDescripcion());
        vacuna.setFechaAplicacion(dto.getFechaAplicacion());
        vacuna.setEstado(1);
        return vacuna;
    }

    public VacunaResponseDTO toResponseDTO(Vacuna entity) {
        if (entity == null) {
            return null;
        }
        VacunaResponseDTO dto = new VacunaResponseDTO();
        dto.setIdVacuna(entity.getIdVacuna());
        dto.setNombreVacuna(entity.getNombreVacuna());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFechaAplicacion(entity.getFechaAplicacion());
        dto.setProximaAplicacion(entity.getProximaAplicacion());

        if (entity.getMascota() != null) {
            dto.setIdMascota(entity.getMascota().getIdMascota());
            dto.setNombreMascota(entity.getMascota().getNombre());
        }
        return dto;
    }

    public void updateEntity(Vacuna entity, VacunaRequestDTO dto) {
        if (entity == null || dto == null) {
            return;
        }
        entity.setNombreVacuna(dto.getNombreVacuna());
        entity.setDescripcion(dto.getDescripcion());
        entity.setFechaAplicacion(dto.getFechaAplicacion());
    }
}