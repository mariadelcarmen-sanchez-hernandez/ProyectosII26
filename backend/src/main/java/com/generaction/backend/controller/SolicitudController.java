package com.generaction.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generaction.backend.dto.AsignacionDTO;
import com.generaction.backend.dto.SolicitudDTO;
import com.generaction.backend.entity.Solicitud;
import com.generaction.backend.entity.Visita;
import com.generaction.backend.service.SolicitudService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SolicitudController {

    private final SolicitudService solicitudService;

    @PostMapping
    public ResponseEntity<Solicitud> crear(@RequestBody SolicitudDTO dto) {
        return ResponseEntity.ok(solicitudService.crearSolicitud(dto));
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<Solicitud>> pendientes() {
        return ResponseEntity.ok(solicitudService.obtenerPendientes());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Solicitud>> disponibles() {
        return ResponseEntity.ok(solicitudService.obtenerPendientes());
    }

    @GetMapping("/pendientes/municipio/{municipio}")
    public ResponseEntity<List<Solicitud>> pendientesPorMunicipio(@PathVariable String municipio) {
        return ResponseEntity.ok(solicitudService.obtenerPendientesPorMunicipio(municipio));
    }

    @GetMapping("/mayor/{id}")
    public ResponseEntity<List<Solicitud>> porMayor(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudService.obtenerPorMayor(id));
    }

    @PostMapping("/asignar")
    public ResponseEntity<Visita> asignar(@RequestBody AsignacionDTO dto) {
        return ResponseEntity.ok(solicitudService.asignarVisita(dto));
    }
}