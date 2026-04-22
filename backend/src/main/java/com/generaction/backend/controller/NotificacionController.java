package com.generaction.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.generaction.backend.dto.NotificacionDTO;
import com.generaction.backend.service.NotificacionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<NotificacionDTO> crear(@RequestBody NotificacionDTO dto) {
        return ResponseEntity.ok(notificacionService.crearNotificacion(dto));
    }

    @GetMapping("/mayor/{idMayor}")
    public ResponseEntity<List<NotificacionDTO>> obtenerPorMayor(@PathVariable Long idMayor) {
        return ResponseEntity.ok(notificacionService.obtenerNotificacionesPorMayor(idMayor));
    }

    @GetMapping("/mayor/{idMayor}/no-leidas")
    public ResponseEntity<List<NotificacionDTO>> obtenerNoLeidas(@PathVariable Long idMayor) {
        return ResponseEntity.ok(notificacionService.obtenerNoLeidasPorMayor(idMayor));
    }

    @PatchMapping("/{id}/leida")
    public ResponseEntity<NotificacionDTO> marcarComoLeida(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.marcarComoLeida(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        notificacionService.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}