package com.veterinaria.gestion_veterinaria.mapper;

import com.veterinaria.gestion_veterinaria.dto.ChipRastreoRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.ChipRastreoResponseDTO;
import com.veterinaria.gestion_veterinaria.model.ChipRastreo;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre ChipRastreo (Entidad) y sus DTOs.
 */
@Component
public class ChipRastreoMapper {

    public ChipRastreo toEntity(ChipRastreoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        ChipRastreo chip = new ChipRastreo();
        chip.setCodigoChip(dto.getCodigoChip());
        chip.setFechaImplantacion(dto.getFechaImplantacion());
        chip.setFabricante(dto.getFabricante());
        chip.setEstado(1);
        return chip;
    }

    public ChipRastreoResponseDTO toResponseDTO(ChipRastreo entity) {
        if (entity == null) {
            return null;
        }
        ChipRastreoResponseDTO dto = new ChipRastreoResponseDTO();
        dto.setIdChip(entity.getIdChip());
        dto.setCodigoChip(entity.getCodigoChip());
        dto.setFechaImplantacion(entity.getFechaImplantacion());
        dto.setFabricante(entity.getFabricante());

        if (entity.getMascota() != null) {
            dto.setIdMascota(entity.getMascota().getIdMascota());
            dto.setNombreMascota(entity.getMascota().getNombre());
        }
        return dto;
    }

    public void updateEntity(ChipRastreo entity, ChipRastreoRequestDTO dto) {
        if (entity == null || dto == null) {
            return;
        }
        entity.setCodigoChip(dto.getCodigoChip());
        entity.setFechaImplantacion(dto.getFechaImplantacion());
        entity.setFabricante(dto.getFabricante());
    }
}