package com.veterinaria.gestion_veterinaria.service;

import com.veterinaria.gestion_veterinaria.dto.CitaSeguimientoRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.CitaSeguimientoResponseDTO;
import com.veterinaria.gestion_veterinaria.exception.ResourceNotFoundException;
import com.veterinaria.gestion_veterinaria.mapper.CitaSeguimientoMapper;
import com.veterinaria.gestion_veterinaria.model.Adoptante;
import com.veterinaria.gestion_veterinaria.model.CitaSeguimiento;
import com.veterinaria.gestion_veterinaria.model.Mascota;
import com.veterinaria.gestion_veterinaria.model.Vacuna;
import com.veterinaria.gestion_veterinaria.repository.CitaSeguimientoRepository;
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
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitaSeguimientoServiceTest {

    @Mock
    private CitaSeguimientoRepository citaRepository;

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private VacunaRepository vacunaRepository;

    @Mock
    private CitaSeguimientoMapper citaMapper;

    @InjectMocks
    private CitaSeguimientoService citaService;

    private CitaSeguimientoRequestDTO requestDTO;
    private CitaSeguimiento cita;
    private CitaSeguimientoResponseDTO responseDTO;
    private Mascota mascota;
    private Adoptante adoptante;
    private Vacuna vacuna;

    @BeforeEach
    void setUp() {
        requestDTO = new CitaSeguimientoRequestDTO();
        requestDTO.setFechaCita(LocalDate.of(2026, 12, 15));
        requestDTO.setMotivo("Revisión general");
        requestDTO.setIdMascota(1L);

        adoptante = new Adoptante();
        adoptante.setIdAdoptante(1L);
        adoptante.setNombre("Juan");
        adoptante.setEstado(1);

        mascota = new Mascota();
        mascota.setIdMascota(1L);
        mascota.setNombre("Firulais");
        mascota.setAdoptante(adoptante);
        mascota.setEstado(1);

        vacuna = new Vacuna();
        vacuna.setIdVacuna(1L);
        vacuna.setNombreVacuna("Rabia");
        vacuna.setMascota(mascota);
        vacuna.setEstado(1);

        cita = new CitaSeguimiento();
        cita.setIdCita(1L);
        cita.setFechaCita(LocalDate.of(2026, 12, 15));
        cita.setMotivo("Revisión general");
        cita.setEstatus("PENDIENTE");
        cita.setMascota(mascota);
        cita.setEstado(1);

        responseDTO = new CitaSeguimientoResponseDTO();
        responseDTO.setIdCita(1L);
        responseDTO.setFechaCita(LocalDate.of(2026, 12, 15));
        responseDTO.setMotivo("Revisión general");
        responseDTO.setEstatus("PENDIENTE");
        responseDTO.setIdMascota(1L);
    }

    @Test
    @DisplayName("Test 1: Programar cita con mascota vacunada exitosamente")
    void testGuardarCita() {
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));
        when(vacunaRepository.findByMascota_IdMascotaAndEstado(1L, 1))
                .thenReturn(Arrays.asList(vacuna));
        when(citaMapper.toEntity(any(CitaSeguimientoRequestDTO.class))).thenReturn(cita);
        when(citaRepository.save(any(CitaSeguimiento.class))).thenReturn(cita);
        when(citaMapper.toResponseDTO(any(CitaSeguimiento.class))).thenReturn(responseDTO);

        CitaSeguimientoResponseDTO resultado = citaService.guardar(requestDTO);

        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstatus());
        assertEquals("Revisión general", resultado.getMotivo());
        verify(citaRepository, times(1)).save(any(CitaSeguimiento.class));
    }
}