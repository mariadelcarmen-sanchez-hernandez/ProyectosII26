package com.generaction.backend.controller;

import com.generaction.backend.repository.AdminRepository;
import com.generaction.backend.repository.MayorRepository;
import com.generaction.backend.repository.VoluntarioRepository;
import com.generaction.backend.service.TokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final MayorRepository mayorRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final AdminRepository adminRepository;
    private final TokenStore tokenStore;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        if (req.email() == null || req.password() == null
                || req.email().isBlank() || req.password().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Email y contraseña requeridos"
            ));
        }

        var adminOpt = adminRepository.findByEmail(req.email());
        if (adminOpt.isPresent()) {
            var admin = adminOpt.get();

            if (admin.getPasswordHash().equals(req.password())) {
                String token = UUID.randomUUID().toString();
                tokenStore.store(token, "ADMIN");

                return ResponseEntity.ok(Map.of(
                        "token", token,
                        "rol", "ADMIN",
                        "nombre", admin.getNombre(),
                        "id", admin.getId()
                ));
            }
        }

        var mayorOpt = mayorRepository.findByEmail(req.email());
        if (mayorOpt.isPresent()) {
            var mayor = mayorOpt.get();

            if (mayor.getPasswordHash().equals(req.password())) {
                String token = UUID.randomUUID().toString();
                tokenStore.store(token, "MAYOR");

                return ResponseEntity.ok(Map.of(
                        "token", token,
                        "rol", "MAYOR",
                        "nombre", mayor.getNombre() + " " + mayor.getApellidos(),
                        "id", mayor.getIdMayor()
                ));
            }
        }

        var voluntarioOpt = voluntarioRepository.findByEmail(req.email());
        if (voluntarioOpt.isPresent()) {
            var voluntario = voluntarioOpt.get();

            if (voluntario.getPasswordHash().equals(req.password())) {
                String token = UUID.randomUUID().toString();
                tokenStore.store(token, "VOLUNTARIO");

                return ResponseEntity.ok(Map.of(
                        "token", token,
                        "rol", "VOLUNTARIO",
                        "nombre", voluntario.getNombre() + " " + voluntario.getApellidos(),
                        "id", voluntario.getIdVoluntario()
                ));
            }
        }

        return ResponseEntity.status(401).body(Map.of(
                "error", "Credenciales incorrectas"
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenStore.remove(token);
        }

        return ResponseEntity.ok(Map.of(
                "message", "Sesión cerrada"
        ));
    }

    public record LoginRequest(String email, String password) {
    }
}