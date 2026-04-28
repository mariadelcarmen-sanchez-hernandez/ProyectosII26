package com.generaction.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generaction.backend.dto.EstadoVisitaDTO;
import com.generaction.backend.dto.RegistroVisitaDTO;
import com.generaction.backend.entity.Visita;
import com.generaction.backend.service.VisitaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/visitas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VisitaController {

    private final VisitaService visitaService;

    @GetMapping
    public ResponseEntity<List<Visita>> listar() {
        return ResponseEntity.ok(visitaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visita> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(visitaService.obtenerPorId(id));
    }

    @GetMapping("/mayor/{idMayor}")
    public ResponseEntity<List<Visita>> listarPorMayor(@PathVariable Long idMayor) {
        return ResponseEntity.ok(visitaService.obtenerPorMayor(idMayor));
    }

    @GetMapping("/voluntario/{idVoluntario}")
    public ResponseEntity<List<Visita>> listarPorVoluntario(@PathVariable Long idVoluntario) {
        return ResponseEntity.ok(visitaService.obtenerPorVoluntario(idVoluntario));
    }

    @PutMapping("/{id}/realizar")
    public ResponseEntity<Visita> registrarRealizacion(
            @PathVariable Long id,
            @RequestBody RegistroVisitaDTO dto) {
        return ResponseEntity.ok(visitaService.registrarRealizacion(id, dto));
    }

    @PutMapping("/{id}/no-realizada")
    public ResponseEntity<Visita> marcarNoRealizada(@PathVariable Long id) {
        return ResponseEntity.ok(visitaService.marcarNoRealizada(id));
    }

    @GetMapping("/voluntario/{idVoluntario}/programadas")
    public ResponseEntity<List<Visita>> listarProgramadasPorVoluntario(@PathVariable Long idVoluntario) {
        return ResponseEntity.ok(visitaService.obtenerProgramadasPorVoluntario(idVoluntario));
    }

    @PutMapping("/{idVisita}/estado")
    public ResponseEntity<Visita> cambiarEstado(
            @PathVariable Long idVisita,
            @RequestBody EstadoVisitaDTO dto) {
        return ResponseEntity.ok(visitaService.actualizarEstado(idVisita, dto));
    }
}