package com.veterinaria.gestion_veterinaria.controller;

import com.veterinaria.gestion_veterinaria.dto.AdoptanteRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.AdoptanteResponseDTO;
import com.veterinaria.gestion_veterinaria.dto.MascotaResponseDTO;
import com.veterinaria.gestion_veterinaria.service.AdoptanteService;
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
 * Controlador REST para la gestión de Adoptantes.
 * Expone los endpoints para CRUD y consultas.
 */
@RestController
@RequestMapping("/api/adoptantes")
@Tag(name = "Adoptantes", description = "Endpoints para gestión de adoptantes")
public class AdoptanteController {

    private final AdoptanteService adoptanteService;
    private final MascotaService mascotaService;

    @Autowired
    public AdoptanteController(AdoptanteService adoptanteService,
                               MascotaService mascotaService) {
        this.adoptanteService = adoptanteService;
        this.mascotaService = mascotaService;
    }

    /**
     * Endpoint 1: Registrar Adoptante.
     * POST /api/adoptantes
     */
    @PostMapping
    @Operation(summary = "Registrar un nuevo adoptante")
    public ResponseEntity<AdoptanteResponseDTO> crear(
            @Valid @RequestBody AdoptanteRequestDTO dto) {
        AdoptanteResponseDTO creado = adoptanteService.guardar(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    /**
     * Endpoint 2: Listar Adoptantes Activos.
     * GET /api/adoptantes
     */
    @GetMapping
    @Operation(summary = "Listar todos los adoptantes activos")
    public ResponseEntity<List<AdoptanteResponseDTO>> listar() {
        List<AdoptanteResponseDTO> lista = adoptanteService.listarActivos();
        return ResponseEntity.ok(lista);
    }

    /**
     * Endpoint 3: Obtener Adoptante por ID.
     * GET /api/adoptantes/{id}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un adoptante por su ID")
    public ResponseEntity<AdoptanteResponseDTO> obtenerPorId(@PathVariable Long id) {
        AdoptanteResponseDTO adoptante = adoptanteService.buscarPorId(id);
        return ResponseEntity.ok(adoptante);
    }

    /**
     * Endpoint 4: Actualizar Adoptante.
     * PUT /api/adoptantes/{id}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un adoptante existente")
    public ResponseEntity<AdoptanteResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody AdoptanteRequestDTO dto) {
        AdoptanteResponseDTO actualizado = adoptanteService.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Endpoint 5: Eliminar Adoptante (Lógico).
     * DELETE /api/adoptantes/{id}
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar lógicamente un adoptante")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        adoptanteService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint 10: Consultar Mascotas por Adoptante.
     * GET /api/adoptantes/{id}/mascotas
     */
    @GetMapping("/{id}/mascotas")
    @Operation(summary = "Listar las mascotas de un adoptante")
    public ResponseEntity<List<MascotaResponseDTO>> listarMascotas(
            @PathVariable Long id) {
        List<MascotaResponseDTO> mascotas = mascotaService.listarPorAdoptante(id);
        return ResponseEntity.ok(mascotas);
    }
}