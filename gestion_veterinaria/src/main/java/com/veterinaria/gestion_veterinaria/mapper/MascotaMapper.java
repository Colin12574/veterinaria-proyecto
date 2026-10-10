package com.veterinaria.gestion_veterinaria.mapper;

import com.veterinaria.gestion_veterinaria.dto.MascotaRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.MascotaResponseDTO;
import com.veterinaria.gestion_veterinaria.model.Mascota;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre Mascota (Entidad) y sus DTOs.
 */
@Component
public class MascotaMapper {

    public Mascota toEntity(MascotaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Mascota mascota = new Mascota();
        mascota.setNombre(dto.getNombre());
        mascota.setEspecie(dto.getEspecie());
        mascota.setRaza(dto.getRaza());
        mascota.setEdad(dto.getEdad());
        mascota.setSexo(dto.getSexo());
        mascota.setFechaIngreso(dto.getFechaIngreso());
        mascota.setFechaAdopcion(dto.getFechaAdopcion());
        mascota.setEstado(1);
        return mascota;
    }

    public MascotaResponseDTO toResponseDTO(Mascota entity) {
        if (entity == null) {
            return null;
        }
        MascotaResponseDTO dto = new MascotaResponseDTO();
        dto.setIdMascota(entity.getIdMascota());
        dto.setNombre(entity.getNombre());
        dto.setEspecie(entity.getEspecie());
        dto.setRaza(entity.getRaza());
        dto.setEdad(entity.getEdad());
        dto.setSexo(entity.getSexo());
        dto.setFechaIngreso(entity.getFechaIngreso());
        dto.setFechaAdopcion(entity.getFechaAdopcion());

        // Datos del adoptante (si existe)
        if (entity.getAdoptante() != null) {
            dto.setIdAdoptante(entity.getAdoptante().getIdAdoptante());
            dto.setNombreAdoptante(entity.getAdoptante().getNombre()
                    + " " + entity.getAdoptante().getApellido());
        }
        return dto;
    }

    public void updateEntity(Mascota entity, MascotaRequestDTO dto) {
        if (entity == null || dto == null) {
            return;
        }
        entity.setNombre(dto.getNombre());
        entity.setEspecie(dto.getEspecie());
        entity.setRaza(dto.getRaza());
        entity.setEdad(dto.getEdad());
        entity.setSexo(dto.getSexo());
        entity.setFechaIngreso(dto.getFechaIngreso());
        entity.setFechaAdopcion(dto.getFechaAdopcion());
    }
}