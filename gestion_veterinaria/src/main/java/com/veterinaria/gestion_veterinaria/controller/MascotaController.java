package com.veterinaria.gestion_veterinaria.controller;

import com.veterinaria.gestion_veterinaria.dto.MascotaRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.MascotaResponseDTO;
import com.veterinaria.gestion_veterinaria.service.MascotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de Mascotas.
 */
@RestController
@RequestMapping("/api/mascotas")
@Tag(name = "Mascotas", description = "Endpoints para gestión de mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    @Autowired
    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    /**
     * Endpoint 6: Registrar Mascota.
     * POST /api/mascotas
     */
    @PostMapping
    @Operation(summary = "Registrar una nueva mascota")
    public ResponseEntity<MascotaResponseDTO> crear(
            @Valid @RequestBody MascotaRequestDTO dto) {
        MascotaResponseDTO creada = mascotaService.guardar(dto);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todas las mascotas activas")
    public ResponseEntity<List<MascotaResponseDTO>> listar() {
        return ResponseEntity.ok(mascotaService.listarActivas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una mascota por su ID")
    public ResponseEntity<MascotaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una mascota existente")
    public ResponseEntity<MascotaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MascotaRequestDTO dto) {
        return ResponseEntity.ok(mascotaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar lógicamente una mascota")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mascotaService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }
}