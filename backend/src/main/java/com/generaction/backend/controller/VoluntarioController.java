package com.generaction.backend.controller;

import com.generaction.backend.dto.VoluntarioDTO;
import com.generaction.backend.entity.Voluntario;
import com.generaction.backend.service.VoluntarioService;
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

    @PostMapping(value = "/{id}/documento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> subirDocumentoVoluntario(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo) {
        try {
            voluntarioService.guardarDocumento(id, archivo);
            return ResponseEntity.ok("Documento del voluntario guardado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar el documento del voluntario: " + e.getMessage());
        }
    }
}