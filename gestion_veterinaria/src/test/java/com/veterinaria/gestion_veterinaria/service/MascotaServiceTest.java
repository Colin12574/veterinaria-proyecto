package com.veterinaria.gestion_veterinaria.service;

import com.veterinaria.gestion_veterinaria.dto.MascotaRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.MascotaResponseDTO;
import com.veterinaria.gestion_veterinaria.exception.ResourceNotFoundException;
import com.veterinaria.gestion_veterinaria.mapper.MascotaMapper;
import com.veterinaria.gestion_veterinaria.model.Adoptante;
import com.veterinaria.gestion_veterinaria.model.Mascota;
import com.veterinaria.gestion_veterinaria.repository.AdoptanteRepository;
import com.veterinaria.gestion_veterinaria.repository.MascotaRepository;
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
class MascotaServiceTest {

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private AdoptanteRepository adoptanteRepository;

    @Mock
    private MascotaMapper mascotaMapper;

    @InjectMocks
    private MascotaService mascotaService;

    private MascotaRequestDTO requestDTO;
    private Mascota mascota;
    private MascotaResponseDTO responseDTO;
    private Adoptante adoptante;

    @BeforeEach
    void setUp() {
        requestDTO = new MascotaRequestDTO();
        requestDTO.setNombre("Firulais");
        requestDTO.setEspecie("Perro");
        requestDTO.setIdAdoptante(1L);

        adoptante = new Adoptante();
        adoptante.setIdAdoptante(1L);
        adoptante.setNombre("Juan");
        adoptante.setEstado(1);

        mascota = new Mascota();
        mascota.setIdMascota(1L);
        mascota.setNombre("Firulais");
        mascota.setEspecie("Perro");
        mascota.setAdoptante(adoptante);
        mascota.setEstado(1);

        responseDTO = new MascotaResponseDTO();
        responseDTO.setIdMascota(1L);
        responseDTO.setNombre("Firulais");
        responseDTO.setEspecie("Perro");
        responseDTO.setIdAdoptante(1L);
    }

    @Test
    @DisplayName("Test 1: Guardar mascota exitosamente")
    void testGuardarMascota() {
        when(adoptanteRepository.findById(1L)).thenReturn(Optional.of(adoptante));
        when(mascotaMapper.toEntity(any(MascotaRequestDTO.class))).thenReturn(mascota);
        when(mascotaRepository.save(any(Mascota.class))).thenReturn(mascota);
        when(mascotaMapper.toResponseDTO(any(Mascota.class))).thenReturn(responseDTO);

        MascotaResponseDTO resultado = mascotaService.guardar(requestDTO);

        assertNotNull(resultado);
        assertEquals("Firulais", resultado.getNombre());
        assertEquals("Perro", resultado.getEspecie());
        verify(mascotaRepository, times(1)).save(any(Mascota.class));
    }

    @Test
    @DisplayName("Test 2: Guardar mascota con adoptante inexistente lanza excepcion")
    void testGuardarMascotaAdoptanteNoExiste() {
        when(adoptanteRepository.findById(999L)).thenReturn(Optional.empty());
        requestDTO.setIdAdoptante(999L);

        assertThrows(ResourceNotFoundException.class, () -> {
            mascotaService.guardar(requestDTO);
        });
    }
}