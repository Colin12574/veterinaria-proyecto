package com.veterinaria.gestion_veterinaria.service;

import com.veterinaria.gestion_veterinaria.dto.MascotaVacunadaDTO;
import com.veterinaria.gestion_veterinaria.model.Mascota;
import com.veterinaria.gestion_veterinaria.model.Vacuna;
import com.veterinaria.gestion_veterinaria.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Servicio para reportes consolidados.
 * Genera el reporte de mascotas vacunadas agrupadas por especie.
 * Cumple con el Endpoint de Consulta Consolidada del PDF.
 */
@Service
public class ReporteService {

    private final MascotaRepository mascotaRepository;

    @Autowired
    public ReporteService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    /**
     * Consulta consolidada: mascotas vacunadas agrupadas por especie.
     *
     * Lógica esperada (PDF):
     * - Consultar mascotas con vacunas aplicadas.
     * - Agrupar por especie.
     * - Ordenar resultados.
     * - Mostrar total de vacunas por mascota.
     */
    public Map<String, List<MascotaVacunadaDTO>> obtenerMascotasVacunadas() {
        // 1. Traer todas las mascotas activas con vacunas activas
        List<Mascota> mascotas = mascotaRepository.findMascotasConVacunasActivas();

        // 2. Convertir cada mascota a DTO con su total de vacunas
        List<MascotaVacunadaDTO> dtos = mascotas.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());

        // 3. Agrupar por especie (TreeMap ordena las claves alfabéticamente)
        Map<String, List<MascotaVacunadaDTO>> agrupado = dtos.stream()
                .collect(Collectors.groupingBy(
                        MascotaVacunadaDTO::getEspecie,
                        TreeMap::new,
                        Collectors.toList()
                ));

        // 4. Ordenar cada lista por nombre de mascota
        agrupado.forEach((especie, lista) ->
                lista.sort(Comparator.comparing(MascotaVacunadaDTO::getNombreMascota))
        );

        return agrupado;
    }

    /**
     * Convierte una Mascota (con sus vacunas) a MascotaVacunadaDTO.
     */
    private MascotaVacunadaDTO convertirADTO(Mascota mascota) {
        // Filtrar solo vacunas activas y obtener sus nombres
        List<String> nombresVacunas = mascota.getVacunas().stream()
                .filter(v -> v.getEstado() == 1)
                .map(Vacuna::getNombreVacuna)
                .collect(Collectors.toList());

        return new MascotaVacunadaDTO(
                mascota.getIdMascota(),
                mascota.getNombre(),
                mascota.getEspecie(),
                (long) nombresVacunas.size(),
                nombresVacunas
        );
    }
}