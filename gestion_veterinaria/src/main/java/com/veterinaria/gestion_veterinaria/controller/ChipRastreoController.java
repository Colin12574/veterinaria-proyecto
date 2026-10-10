package com.veterinaria.gestion_veterinaria.controller;

import com.veterinaria.gestion_veterinaria.dto.ChipRastreoRequestDTO;
import com.veterinaria.gestion_veterinaria.dto.ChipRastreoResponseDTO;
import com.veterinaria.gestion_veterinaria.service.ChipRastreoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de Chips de Rastreo.
 */
@RestController
@RequestMapping("/api/chips")
@Tag(name = "Chips", description = "Endpoints para gestión de chips de rastreo")
public class ChipRastreoController {

    private final ChipRastreoService chipService;

    @Autowired
    public ChipRastreoController(ChipRastreoService chipService) {
        this.chipService = chipService;
    }

    /**
     * Endpoint 8: Registrar Chip de Rastreo.
     * POST /api/chips
     */
    @PostMapping
    @Operation(summary = "Registrar un chip de rastreo para una mascota")
    public ResponseEntity<ChipRastreoResponseDTO> crear(
            @Valid @RequestBody ChipRastreoRequestDTO dto) {
        ChipRastreoResponseDTO creado = chipService.guardar(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todos los chips activos")
    public ResponseEntity<List<ChipRastreoResponseDTO>> listar() {
        return ResponseEntity.ok(chipService.listarActivos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un chip por su ID")
    public ResponseEntity<ChipRastreoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(chipService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar lógicamente un chip")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        chipService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }
}