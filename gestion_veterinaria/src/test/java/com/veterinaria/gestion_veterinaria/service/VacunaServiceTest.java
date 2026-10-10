package com.veterinaria.gestion_veterinaria.service;

import com.veterinaria.gestion_veterinaria.dto.VacunaRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.VacunaResponseDTO;
import com.veterinaria.gestion_veterinaria.exception.ResourceNotFoundException;
import com.veterinaria.gestion_veterinaria.mapper.VacunaMapper;
import com.veterinaria.gestion_veterinaria.model.Mascota;
import com.veterinaria.gestion_veterinaria.model.Vacuna;
import com.veterinaria.gestion_veterinaria.repository.MascotaRepository;
import com.veterinaria.gestion_veterinaria.repository.VacunaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacunaServiceTest {

    @Mock
    private VacunaRepository vacunaRepository;

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private VacunaMapper vacunaMapper;

    @InjectMocks
    private VacunaService vacunaService;

    private VacunaRequestDTO requestDTO;
    private Vacuna vacuna;
    private VacunaResponseDTO responseDTO;
    private Mascota mascota;

    @BeforeEach
    void setUp() {
        requestDTO = new VacunaRequestDTO();
        requestDTO.setNombreVacuna("Rabia");
        requestDTO.setFechaAplicacion(LocalDate.of(2026, 10, 1));
        requestDTO.setIdMascota(1L);

        mascota = new Mascota();
        mascota.setIdMascota(1L);
        mascota.setNombre("Firulais");
        mascota.setEstado(1);

        vacuna = new Vacuna();
        vacuna.setIdVacuna(1L);
        vacuna.setNombreVacuna("Rabia");
        vacuna.setFechaAplicacion(LocalDate.of(2026, 10, 1));
        vacuna.setProximaAplicacion(LocalDate.of(2027, 10, 1));
        vacuna.setMascota(mascota);
        vacuna.setEstado(1);

        responseDTO = new VacunaResponseDTO();
        responseDTO.setIdVacuna(1L);
        responseDTO.setNombreVacuna("Rabia");
        responseDTO.setFechaAplicacion(LocalDate.of(2026, 10, 1));
        responseDTO.setProximaAplicacion(LocalDate.of(2027, 10, 1));
        responseDTO.setIdMascota(1L);
    }

    @Test
    @DisplayName("Test 1: Guardar vacuna y calcular proxima aplicacion")
    void testGuardarVacuna() {
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));
        when(vacunaMapper.toEntity(any(VacunaRequestDTO.class))).thenReturn(vacuna);
        when(vacunaRepository.save(any(Vacuna.class))).thenReturn(vacuna);
        when(vacunaMapper.toResponseDTO(any(Vacuna.class))).thenReturn(responseDTO);

        VacunaResponseDTO resultado = vacunaService.guardar(requestDTO);

        assertNotNull(resultado);
        assertEquals("Rabia", resultado.getNombreVacuna());
        assertNotNull(resultado.getProximaAplicacion());
        verify(vacunaRepository, times(1)).save(any(Vacuna.class));
    }

    @Test
    @DisplayName("Test 2: Guardar vacuna con mascota inexistente lanza excepcion")
    void testGuardarVacunaMascotaNoExiste() {
        when(mascotaRepository.findById(999L)).thenReturn(Optional.empty());
        requestDTO.setIdMascota(999L);

        assertThrows(ResourceNotFoundException.class, () -> {
            vacunaService.guardar(requestDTO);
        });
    }
}