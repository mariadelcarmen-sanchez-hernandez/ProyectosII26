package com.generaction.backend.controller;

import com.generaction.backend.dto.AsignacionDTO;
import com.generaction.backend.dto.SolicitudDTO;
import com.generaction.backend.entity.Solicitud;
import com.generaction.backend.entity.Visita;
import com.generaction.backend.service.SolicitudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SolicitudController {

    private final SolicitudService solicitudService;

    // POST /api/solicitudes → crear solicitud (HU3)
    @PostMapping
    public ResponseEntity<Solicitud> crear(@RequestBody SolicitudDTO dto) {
        return ResponseEntity.ok(solicitudService.crearSolicitud(dto));
    }

    // GET /api/solicitudes/pendientes → listado para voluntarios (HU4)
    @GetMapping("/pendientes")
    public ResponseEntity<List<Solicitud>> pendientes() {
        return ResponseEntity.ok(solicitudService.obtenerPendientes());
    }

    // GET /api/solicitudes/pendientes?municipio=Madrid → filtradas por zona
    @GetMapping("/pendientes/municipio/{municipio}")
    public ResponseEntity<List<Solicitud>> pendientesPorMunicipio(
            @PathVariable String municipio) {
        return ResponseEntity.ok(
            solicitudService.obtenerPendientesPorMunicipio(municipio)
        );
    }

    // GET /api/solicitudes/mayor/{id} → historial de un mayor
    @GetMapping("/mayor/{id}")
    public ResponseEntity<List<Solicitud>> porMayor(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudService.obtenerPorMayor(id));
    }

    // POST /api/solicitudes/asignar → voluntario acepta solicitud (HU4)
    @PostMapping("/asignar")
    public ResponseEntity<Visita> asignar(@RequestBody AsignacionDTO dto) {
        return ResponseEntity.ok(solicitudService.asignarVisita(dto));
    }
}