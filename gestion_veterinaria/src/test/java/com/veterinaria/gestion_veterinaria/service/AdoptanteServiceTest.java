package com.veterinaria.gestion_veterinaria.service;

import com.veterinaria.gestion_veterinaria.dto.AdoptanteRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.AdoptanteResponseDTO;
import com.veterinaria.gestion_veterinaria.exception.ResourceNotFoundException;
import com.veterinaria.gestion_veterinaria.mapper.AdoptanteMapper;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdoptanteServiceTest {

    @Mock
    private AdoptanteRepository adoptanteRepository;

    @Mock
    private AdoptanteMapper adoptanteMapper;

    @InjectMocks
    private AdoptanteService adoptanteService;

    private AdoptanteRequestDTO requestDTO;
    private Adoptante adoptante;
    private AdoptanteResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new AdoptanteRequestDTO();
        requestDTO.setNombre("Juan");
        requestDTO.setApellido("Pérez");
        requestDTO.setCorreo("juan@mail.com");

        adoptante = new Adoptante();
        adoptante.setIdAdoptante(1L);
        adoptante.setNombre("Juan");
        adoptante.setApellido("Pérez");
        adoptante.setCorreo("juan@mail.com");
        adoptante.setEstado(1);

        responseDTO = new AdoptanteResponseDTO();
        responseDTO.setIdAdoptante(1L);
        responseDTO.setNombre("Juan");
        responseDTO.setApellido("Pérez");
        responseDTO.setCorreo("juan@mail.com");
    }

    @Test
    @DisplayName("Test 1: Guardar adoptante exitosamente")
    void testGuardarAdoptante() {
        when(adoptanteMapper.toEntity(any(AdoptanteRequestDTO.class))).thenReturn(adoptante);
        when(adoptanteRepository.save(any(Adoptante.class))).thenReturn(adoptante);
        when(adoptanteMapper.toResponseDTO(any(Adoptante.class))).thenReturn(responseDTO);

        AdoptanteResponseDTO resultado = adoptanteService.guardar(requestDTO);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Pérez", resultado.getApellido());
        verify(adoptanteRepository, times(1)).save(any(Adoptante.class));
    }

    @Test
    @DisplayName("Test 2: Buscar adoptante por ID existente")
    void testBuscarPorId() {
        when(adoptanteRepository.findById(1L)).thenReturn(Optional.of(adoptante));
        when(adoptanteMapper.toResponseDTO(any(Adoptante.class))).thenReturn(responseDTO);

        AdoptanteResponseDTO resultado = adoptanteService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdAdoptante());
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    @DisplayName("Test 3: Buscar adoptante inexistente lanza excepcion")
    void testBuscarPorIdNoExistente() {
        when(adoptanteRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            adoptanteService.buscarPorId(999L);
        });
    }
}