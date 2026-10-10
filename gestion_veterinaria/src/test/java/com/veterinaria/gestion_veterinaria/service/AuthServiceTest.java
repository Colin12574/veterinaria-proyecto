package com.veterinaria.gestion_veterinaria.service;

import com.veterinaria.gestion_veterinaria.dto.LoginRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.LoginResponseDTO;
import com.veterinaria.gestion_veterinaria.exception.ResourceNotFoundException;
import com.veterinaria.gestion_veterinaria.model.Adoptante;
import com.veterinaria.gestion_veterinaria.repository.AdoptanteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AdoptanteRepository adoptanteRepository;

    @InjectMocks
    private AuthService authService;

    private LoginRequestDTO requestDTO;
    private Adoptante admin;

    @BeforeEach
    void setUp() {
        requestDTO = new LoginRequestDTO();
        requestDTO.setCorreo("admin@veterinaria.com");

        admin = new Adoptante();
        admin.setIdAdoptante(1L);
        admin.setNombre("Admin");
        admin.setApellido("Veterinaria");
        admin.setCorreo("admin@veterinaria.com");
        admin.setRol("ADMIN");
        admin.setEstado(1);
    }

    @Test
    @DisplayName("Test 1: Login exitoso devuelve rol ADMIN")
    void testLoginExitoso() {
        when(adoptanteRepository.findByCorreo("admin@veterinaria.com"))
                .thenReturn(Optional.of(admin));

        LoginResponseDTO resultado = authService.login(requestDTO);

        assertNotNull(resultado);
        assertEquals("admin@veterinaria.com", resultado.getCorreo());
        assertEquals("ADMIN", resultado.getRol());
    }

    @Test
    @DisplayName("Test 2: Login con correo inexistente lanza excepcion")
    void testLoginCorreoNoExiste() {
        requestDTO.setCorreo("noexiste@mail.com");
        when(adoptanteRepository.findByCorreo("noexiste@mail.com"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            authService.login(requestDTO);
        });
    }
}