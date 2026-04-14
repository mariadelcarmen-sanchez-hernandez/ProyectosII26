package com.generaction.backend.controller;

import com.generaction.backend.dto.MayorDTO;
import com.generaction.backend.entity.Mayor;
import com.generaction.backend.service.MayorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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


    @PostMapping(value = "/{id}/documento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> subirDocumentoMayor(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo) {
        try {
            mayorService.guardarDocumento(id, archivo);
            return ResponseEntity.ok("Documento del mayor guardado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar el documento del mayor: " + e.getMessage());
        }
    }
}