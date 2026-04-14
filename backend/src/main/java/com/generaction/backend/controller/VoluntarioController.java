package com.generaction.backend.controller;

import com.generaction.backend.dto.VoluntarioDTO;
import com.generaction.backend.entity.Voluntario;
import com.generaction.backend.service.VoluntarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voluntarios")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VoluntarioController {

    private final VoluntarioService voluntarioService;

    // POST /api/voluntarios  → registrar voluntario (HU2)
    @PostMapping
    public ResponseEntity<Voluntario> registrar(@RequestBody VoluntarioDTO dto) {
        Voluntario nuevo = voluntarioService.registrarVoluntario(dto);
        return ResponseEntity.ok(nuevo);
    }

    // GET /api/voluntarios
    @GetMapping
    public ResponseEntity<List<Voluntario>> listar() {
        return ResponseEntity.ok(voluntarioService.obtenerVoluntariosActivos());
    }

    // GET /api/voluntarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Voluntario> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(voluntarioService.obtenerPorId(id));
    }
}