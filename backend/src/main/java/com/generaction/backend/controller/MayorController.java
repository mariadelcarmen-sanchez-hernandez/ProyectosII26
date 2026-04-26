package com.generaction.backend.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.generaction.backend.dto.MayorDTO;
import com.generaction.backend.entity.Mayor;
import com.generaction.backend.service.MayorService;

import lombok.RequiredArgsConstructor;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MayorDTO dto) {
        Mayor mayor = mayorService.loginMayor(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(mayor);
    }

    // POST /api/mayores/{id}/documento -> guarda PDF validando DNI
    @PostMapping("/{id}/documento")
    public ResponseEntity<?> subirDocumento(
            @PathVariable Long id,
            @RequestParam("dni") String dni,
            @RequestParam("archivo") MultipartFile archivo
    ) {
        try {
            mayorService.guardarDocumento(id, dni, archivo);
            return ResponseEntity.ok(Map.of("mensaje", "Documento guardado correctamente."));
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo procesar el archivo PDF.");
        }
    }

}