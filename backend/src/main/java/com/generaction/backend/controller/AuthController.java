package com.generaction.backend.controller;

import com.generaction.backend.repository.MayorRepository;
import com.generaction.backend.repository.VoluntarioRepository;
import com.generaction.backend.service.TokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final TokenStore tokenStore;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.admin.nombre}")
    private String adminNombre;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        if (req.email() == null || req.password() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email y contraseña requeridos"));
        }

        // Check admin credentials first
        if (adminEmail.equals(req.email()) && adminPassword.equals(req.password())) {
            String token = UUID.randomUUID().toString();
            tokenStore.store(token, "ADMIN");
            return ResponseEntity.ok(Map.of(
                "token", token,
                "rol", "ADMIN",
                "nombre", adminNombre,
                "id", 0
            ));
        }

        // Check Mayor table
        var mayor = mayorRepository.findByEmail(req.email());
        if (mayor.isPresent() && mayor.get().getPasswordHash().equals(req.password())) {
            var m = mayor.get();
            String token = UUID.randomUUID().toString();
            tokenStore.store(token, "MAYOR");
            return ResponseEntity.ok(Map.of(
                "token", token,
                "rol", "MAYOR",
                "nombre", m.getNombre() + " " + m.getApellidos(),
                "id", m.getIdMayor()
            ));
        }

        // Check Voluntario table
        var voluntario = voluntarioRepository.findByEmail(req.email());
        if (voluntario.isPresent() && voluntario.get().getPasswordHash().equals(req.password())) {
            var v = voluntario.get();
            String token = UUID.randomUUID().toString();
            tokenStore.store(token, "VOLUNTARIO");
            return ResponseEntity.ok(Map.of(
                "token", token,
                "rol", "VOLUNTARIO",
                "nombre", v.getNombre() + " " + v.getApellidos(),
                "id", v.getIdVoluntario()
            ));
        }

        return ResponseEntity.status(401).body(Map.of("error", "Credenciales incorrectas"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String auth) {
        if (auth != null && auth.startsWith("Bearer ")) {
            tokenStore.remove(auth.substring(7));
        }
        return ResponseEntity.ok(Map.of("message", "Sesión cerrada"));
    }

    record LoginRequest(String email, String password) {}
}
