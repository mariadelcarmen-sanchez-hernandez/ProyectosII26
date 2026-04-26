package com.generaction.backend.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.generaction.backend.dto.MayorDTO;
import com.generaction.backend.entity.Mayor;
import com.generaction.backend.repository.MayorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MayorService {

    private static final Set<String> NIVELES_VALIDOS = Set.of("alto", "medio", "bajo");
    
    private final MayorRepository mayorRepository;
    private final DniValidationService dniValidationService;

    // HU1 — Registrar un mayor
    public Mayor registrarMayor(MayorDTO dto) {
        if (mayorRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Ya existe una persona mayor con ese email.");
        }

        Mayor mayor = new Mayor();
        mayor.setNombre(dto.getNombre());
        mayor.setApellidos(dto.getApellidos());
        mayor.setEmail(dto.getEmail());
        mayor.setPasswordHash(dto.getPassword());
        mayor.setTelefono(dto.getTelefono());
        mayor.setDireccion(dto.getDireccion() != null ? dto.getDireccion() : "");
        mayor.setMunicipio(dto.getMunicipio());
        mayor.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));
        mayor.setNivelAutonomia(dto.getNivelAutonomia());
        mayor.setPreferenciasActividad(dto.getPreferenciasActividad());
        mayor.setContactoFamiliarNombre(dto.getContactoFamiliarNombre());
        mayor.setContactoFamiliarTelefono(dto.getContactoFamiliarTelefono());
        mayor.setActivo(true);
        mayor.setFechaAlta(LocalDateTime.now());

        return mayorRepository.save(mayor);
    }

    public Mayor loginMayor(String email, String password) {
        Mayor mayor = mayorRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("No existe una cuenta de mayor con ese correo."));

        if (!mayor.getPasswordHash().equals(password)) {
            throw new RuntimeException("Contraseña incorrecta.");
        }

        return mayor;
    }

    // Obtener todos los mayores activos
    public List<Mayor> obtenerMayoresActivos() {
        return mayorRepository.findByActivoTrue();
    }

    // Obtener mayor por ID
    public Mayor obtenerPorId(Long id) {
        return mayorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mayor no encontrado con id: " + id));
    }

    // Actualizar datos del mayor
    public Mayor actualizarMayor(Long id, MayorDTO dto) {
        Mayor mayor = obtenerPorId(id);

        if (dto.getNombre() != null) mayor.setNombre(dto.getNombre());
        if (dto.getApellidos() != null) mayor.setApellidos(dto.getApellidos());
        if (dto.getTelefono() != null) mayor.setTelefono(dto.getTelefono());
        if (dto.getDireccion() != null) mayor.setDireccion(dto.getDireccion());
        if (dto.getMunicipio() != null) mayor.setMunicipio(dto.getMunicipio());

        if (dto.getFechaNacimiento() != null && !dto.getFechaNacimiento().isBlank()) {
            mayor.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));
        }

        if (dto.getNivelAutonomia() != null && !dto.getNivelAutonomia().isBlank()) {
            String nivelAutonomia = dto.getNivelAutonomia().trim().toLowerCase();
            if (!NIVELES_VALIDOS.contains(nivelAutonomia)) {
                throw new RuntimeException("Nivel de autonomía no válido.");
            }
            mayor.setNivelAutonomia(nivelAutonomia);
        }

        if (dto.getPreferenciasActividad() != null) {
            mayor.setPreferenciasActividad(dto.getPreferenciasActividad());
        }

        if (dto.getContactoFamiliarNombre() != null) {
            mayor.setContactoFamiliarNombre(dto.getContactoFamiliarNombre());
        }

        if (dto.getContactoFamiliarTelefono() != null) {
            mayor.setContactoFamiliarTelefono(dto.getContactoFamiliarTelefono());
        }

        return mayorRepository.save(mayor);
    }

    // Dar de baja (no borrar, solo marcar inactivo)
    public void darDeBaja(Long id) {
        Mayor mayor = obtenerPorId(id);
        mayor.setActivo(false);
        mayorRepository.save(mayor);
    }

    // Guardar documento PDF del mayor (HU2)
    public void guardarDocumento(Long id, String dni, MultipartFile archivo) throws IOException {
        var resultadoDni = dniValidationService.validar(dni);
        if (!resultadoDni.valido()) {
            throw new RuntimeException("DNI invalido: " + resultadoDni.error());
        }
        if (archivo == null || archivo.isEmpty()) {
            throw new RuntimeException("Debe adjuntar un archivo PDF.");
        }
        String fileName = archivo.getOriginalFilename() != null ? archivo.getOriginalFilename().toLowerCase() : "";
        String contentType = archivo.getContentType() != null ? archivo.getContentType().toLowerCase() : "";
        if (!fileName.endsWith(".pdf") && !"application/pdf".equals(contentType)) {
            throw new RuntimeException("El documento debe ser un PDF.");
        }

        Mayor mayor = mayorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mayor no encontrado"));

        mayor.setDocumentoPdf(archivo.getBytes());
        mayorRepository.save(mayor);
    }
}