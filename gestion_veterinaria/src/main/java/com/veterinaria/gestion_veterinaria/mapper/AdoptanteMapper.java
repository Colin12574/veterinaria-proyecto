package com.veterinaria.gestion_veterinaria.mapper;

import com.veterinaria.gestion_veterinaria.dto.AdoptanteRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.AdoptanteResponseDTO;
import com.veterinaria.gestion_veterinaria.model.Adoptante;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre Adoptante (Entidad) y sus DTOs.
 */
@Component
public class AdoptanteMapper {

    public Adoptante toEntity(AdoptanteRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Adoptante adoptante = new Adoptante();
        adoptante.setNombre(dto.getNombre());
        adoptante.setApellido(dto.getApellido());
        adoptante.setTelefono(dto.getTelefono());
        adoptante.setCorreo(dto.getCorreo());
        adoptante.setDireccion(dto.getDireccion());
        adoptante.setRol("CLIENTE");
        adoptante.setEstado(1);
        return adoptante;
    }

    public AdoptanteResponseDTO toResponseDTO(Adoptante entity) {
        if (entity == null) {
            return null;
        }
        AdoptanteResponseDTO dto = new AdoptanteResponseDTO();
        dto.setIdAdoptante(entity.getIdAdoptante());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setTelefono(entity.getTelefono());
        dto.setCorreo(entity.getCorreo());
        dto.setDireccion(entity.getDireccion());
        dto.setRol(entity.getRol());
        return dto;
    }

    public void updateEntity(Adoptante entity, AdoptanteRequestDTO dto) {
        if (entity == null || dto == null) {
            return;
        }
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setTelefono(dto.getTelefono());
        entity.setCorreo(dto.getCorreo());
        entity.setDireccion(dto.getDireccion());
    }
}