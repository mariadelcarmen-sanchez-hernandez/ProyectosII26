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

import com.generaction.backend.dto.ValoracionDTO;
import com.generaction.backend.entity.Valoracion;
import com.generaction.backend.service.ValoracionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/valoraciones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ValoracionController {

    private final ValoracionService valoracionService;

    @PostMapping
    public ResponseEntity<Valoracion> crear(@RequestBody ValoracionDTO dto) {
        return ResponseEntity.ok(valoracionService.crearValoracion(dto));
    }

    @GetMapping
    public ResponseEntity<List<Valoracion>> listarTodas() {
        return ResponseEntity.ok(valoracionService.obtenerTodas());
    }

    @GetMapping("/visita/{idVisita}")
    public ResponseEntity<List<Valoracion>> listarPorVisita(@PathVariable Long idVisita) {
        return ResponseEntity.ok(valoracionService.obtenerPorVisita(idVisita));
    }
}
