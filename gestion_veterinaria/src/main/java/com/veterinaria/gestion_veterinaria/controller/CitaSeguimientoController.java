package com.veterinaria.gestion_veterinaria.controller;

import com.veterinaria.gestion_veterinaria.dto.CitaSeguimientoRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.CitaSeguimientoResponseDTO;
import com.veterinaria.gestion_veterinaria.service.CitaSeguimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@Tag(name = "Citas", description = "Endpoints para gestión de citas de seguimiento")
public class CitaSeguimientoController {

    private final CitaSeguimientoService citaService;

    @Autowired
    public CitaSeguimientoController(CitaSeguimientoService citaService) {
        this.citaService = citaService;
    }

    @PostMapping
    @Operation(summary = "Programar una cita de seguimiento")
    public ResponseEntity<CitaSeguimientoResponseDTO> crear(
            @Valid @RequestBody CitaSeguimientoRequestDTO dto) {
        CitaSeguimientoResponseDTO creada = citaService.guardar(dto);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todas las citas activas (ADMIN)")
    public ResponseEntity<List<CitaSeguimientoResponseDTO>> listar() {
        return ResponseEntity.ok(citaService.listarActivas());
    }

    // ============================================================
    // NUEVO ENDPOINT: Listar citas por adoptante (CLIENTE)
    // ============================================================
    @GetMapping("/adoptante/{idAdoptante}")
    @Operation(summary = "Listar citas de un adoptante específico")
    public ResponseEntity<List<CitaSeguimientoResponseDTO>> listarPorAdoptante(
            @PathVariable Long idAdoptante) {
        return ResponseEntity.ok(citaService.listarPorAdoptante(idAdoptante));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una cita por su ID")
    public ResponseEntity<CitaSeguimientoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar una cita de seguimiento")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        citaService.cancelarCita(id);
        return ResponseEntity.noContent().build();
    }
}