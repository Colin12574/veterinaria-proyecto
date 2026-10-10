package com.veterinaria.gestion_veterinaria.controller;

import com.veterinaria.gestion_veterinaria.dto.MascotaVacunadaDTO;
import com.veterinaria.gestion_veterinaria.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para reportes consolidados.
 * Expone el endpoint del reporte de mascotas vacunadas.
 */
@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "Endpoints para reportes consolidados")
public class ReporteController {

    private final ReporteService reporteService;

    @Autowired
    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    /**
     * Consulta Consolidada: Mascotas Vacunadas.
     * GET /api/reportes/mascotas-vacunadas
     *
     * Devuelve las mascotas con vacunas aplicadas, agrupadas por especie,
     * ordenadas alfabéticamente, mostrando el total de vacunas por mascota.
     */
    @GetMapping("/mascotas-vacunadas")
    @Operation(summary = "Reporte de mascotas vacunadas agrupadas por especie")
    public ResponseEntity<Map<String, List<MascotaVacunadaDTO>>> mascotasVacunadas() {
        Map<String, List<MascotaVacunadaDTO>> reporte =
                reporteService.obtenerMascotasVacunadas();
        return ResponseEntity.ok(reporte);
    }
}