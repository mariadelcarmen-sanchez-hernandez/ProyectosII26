package com.generaction.backend.controller;

import com.generaction.backend.dto.MayorDTO;
import com.generaction.backend.entity.Mayor;
import com.generaction.backend.service.MayorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mayores")
@CrossOrigin(origins = "*")   // Permite llamadas desde tu HTML/React
@RequiredArgsConstructor
public class MayorController {

    private final MayorService mayorService;

    // POST /api/mayores  → registrar un mayor (HU1)
    @PostMapping
    public ResponseEntity<Mayor> registrar(@RequestBody MayorDTO dto) {
        Mayor nuevo = mayorService.registrarMayor(dto);
        return ResponseEntity.ok(nuevo);
    }

    // GET /api/mayores  → listar todos los activos
    @GetMapping
    public ResponseEntity<List<Mayor>> listar() {
        return ResponseEntity.ok(mayorService.obtenerMayoresActivos());
    }

    // GET /api/mayores/{id}  → obtener uno por ID
    @GetMapping("/{id}")
    public ResponseEntity<Mayor> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(mayorService.obtenerPorId(id));
    }

    // DELETE /api/mayores/{id}  → dar de baja
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> darDeBaja(@PathVariable Long id) {
        mayorService.darDeBaja(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH /api/mayores/{id} → Actualizar datos del mayor
    @PutMapping("/{id}")
    public ResponseEntity<Mayor> actualizar(@PathVariable Long id, @RequestBody MayorDTO dto) {
        Mayor actualizado = mayorService.actualizarMayor(id, dto);
        return ResponseEntity.ok(actualizado);
    }
}