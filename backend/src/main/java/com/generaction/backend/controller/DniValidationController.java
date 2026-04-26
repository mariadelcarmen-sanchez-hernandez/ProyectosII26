package com.generaction.backend.controller;

import java.time.OffsetDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generaction.backend.service.DniValidationService;
import com.generaction.backend.service.DniValidationService.DniValidationResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dni")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DniValidationController {

    private final DniValidationService dniValidationService;

    @PostMapping("/validar")
    public ResponseEntity<?> validarPost(@RequestBody Map<String, String> body) {
        String dni = body != null ? body.get("dni") : null;
        DniValidationResult resultado = dniValidationService.validar(dni);

        if (!resultado.valido() && "DNI_REQUERIDO".equals(resultado.codigo())) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", resultado.error(),
                    "codigo", resultado.codigo()
            ));
        }

        return ResponseEntity.ok(Map.of(
                "dni", dni,
                "timestamp", OffsetDateTime.now().toString(),
                "resultado", resultado
        ));
    }

    @GetMapping("/validar/{dni}")
    public ResponseEntity<?> validarGet(@PathVariable String dni) {
        DniValidationResult resultado = dniValidationService.validar(dni);
        return ResponseEntity.ok(Map.of(
                "dni", dni,
                "valido", resultado.valido(),
                "timestamp", OffsetDateTime.now().toString()
        ));
    }
}