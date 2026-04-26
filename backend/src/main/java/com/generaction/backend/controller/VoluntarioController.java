package com.generaction.backend.controller;

import com.generaction.backend.dto.VoluntarioDTO;
import com.generaction.backend.entity.MovimientoWallet;
import com.generaction.backend.entity.Voluntario;
import com.generaction.backend.service.VoluntarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    
    // POST /api/voluntarios/{id}/documento -> guarda PDF validando DNI
    @PostMapping("/{id}/documento")
    public ResponseEntity<?> subirDocumento(
            @PathVariable Long id,
            @RequestParam("dni") String dni,
            @RequestParam("archivo") MultipartFile archivo
    ) {
        try {
            voluntarioService.guardarDocumento(id, dni, archivo);
            return ResponseEntity.ok(Map.of("mensaje", "Documento guardado correctamente."));
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo procesar el archivo PDF.");
        }
    }

    @GetMapping("/{id}/documento")
    public ResponseEntity<byte[]> obtenerDocumento(@PathVariable Long id) {
        Voluntario v = voluntarioService.obtenerPorId(id);

        if (v.getDocumentoPdf() == null || v.getDocumentoPdf().length == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "inline; filename=documento.pdf")
                .body(v.getDocumentoPdf());
    }

    @GetMapping("/{id}/wallet")
    public ResponseEntity<Map<String, Object>> obtenerWallet(@PathVariable Long id) {
        Voluntario voluntario = voluntarioService.obtenerPorId(id);
        return ResponseEntity.ok(Map.of(
                "idVoluntario", voluntario.getIdVoluntario(),
                "puntosWallet", voluntario.getPuntosWallet()
        ));
    }

    @PostMapping("/{id}/wallet/sumar")
    public ResponseEntity<Map<String, Object>> sumarPuntos(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> body
    ) {
        Integer puntos = body.get("puntos");
        Voluntario actualizado = voluntarioService.sumarPuntos(id, puntos);
        return ResponseEntity.ok(Map.of(
                "mensaje", "Puntos sumados correctamente",
                "puntosWallet", actualizado.getPuntosWallet()
        ));
    }

    @PostMapping("/{id}/wallet/restar")
    public ResponseEntity<Map<String, Object>> restarPuntos(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> body
    ) {
        Integer puntos = body.get("puntos");
        Voluntario actualizado = voluntarioService.restarPuntos(id, puntos);
        return ResponseEntity.ok(Map.of(
                "mensaje", "Puntos restados correctamente",
                "puntosWallet", actualizado.getPuntosWallet()
        ));
    }

    @GetMapping("/{id}/wallet/movimientos")
    public ResponseEntity<List<MovimientoWallet>> obtenerMovimientosWallet(@PathVariable Long id) {
        return ResponseEntity.ok(voluntarioService.obtenerMovimientosWallet(id));
    }

}