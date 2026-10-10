package com.veterinaria.gestion_veterinaria.service;

import com.veterinaria.gestion_veterinaria.dto.LoginRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.LoginResponseDTO;
import com.veterinaria.gestion_veterinaria.exception.ResourceNotFoundException;
import com.veterinaria.gestion_veterinaria.model.Adoptante;
import com.veterinaria.gestion_veterinaria.repository.AdoptanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio de autenticación usando la tabla ADOPTANTE.
 */
@Service
public class AuthService {

    private final AdoptanteRepository adoptanteRepository;

    @Autowired
    public AuthService(AdoptanteRepository adoptanteRepository) {
        this.adoptanteRepository = adoptanteRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        Adoptante adoptante = adoptanteRepository.findByCorreo(dto.getCorreo())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado con correo: " + dto.getCorreo()));

        if (adoptante.getEstado() == 0) {
            throw new IllegalStateException("El usuario está inactivo");
        }

        return convertirAResponse(adoptante);
    }

    public LoginResponseDTO registrar(Adoptante adoptante) {
        if (adoptanteRepository.findByCorreo(adoptante.getCorreo()).isPresent()) {
            throw new IllegalStateException(
                    "Ya existe un usuario con el correo: " + adoptante.getCorreo());
        }

        adoptante.setRol("CLIENTE");
        adoptante.setEstado(1);

        Adoptante guardado = adoptanteRepository.save(adoptante);
        return convertirAResponse(guardado);
    }

    private LoginResponseDTO convertirAResponse(Adoptante a) {
        return new LoginResponseDTO(
                a.getIdAdoptante(),
                a.getNombre(),
                a.getApellido(),
                a.getCorreo(),
                a.getTelefono(),
                a.getRol()
        );
    }
}