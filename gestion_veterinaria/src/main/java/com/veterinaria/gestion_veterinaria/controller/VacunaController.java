package com.veterinaria.gestion_veterinaria.controller;

import com.veterinaria.gestion_veterinaria.dto.VacunaRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.VacunaResponseDTO;
import com.veterinaria.gestion_veterinaria.service.VacunaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacunas")
@Tag(name = "Vacunas", description = "Endpoints para gestión de vacunas")
public class VacunaController {

    private final VacunaService vacunaService;

    @Autowired
    public VacunaController(VacunaService vacunaService) {
        this.vacunaService = vacunaService;
    }

    @PostMapping
    @Operation(summary = "Registrar una vacuna aplicada a una mascota")
    public ResponseEntity<VacunaResponseDTO> crear(
            @Valid @RequestBody VacunaRequestDTO dto) {
        VacunaResponseDTO creada = vacunaService.guardar(dto);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todas las vacunas activas (ADMIN)")
    public ResponseEntity<List<VacunaResponseDTO>> listar() {
        return ResponseEntity.ok(vacunaService.listarActivas());
    }

    // ============================================================
    // NUEVO ENDPOINT: Listar vacunas de todas las mascotas de un adoptante
    // ============================================================
    @GetMapping("/adoptante/{idAdoptante}")
    @Operation(summary = "Listar vacunas de las mascotas de un adoptante")
    public ResponseEntity<List<VacunaResponseDTO>> listarPorAdoptante(
            @PathVariable Long idAdoptante) {
        return ResponseEntity.ok(vacunaService.listarPorAdoptante(idAdoptante));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una vacuna por su ID")
    public ResponseEntity<VacunaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vacunaService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar lógicamente una vacuna")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        vacunaService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }
}