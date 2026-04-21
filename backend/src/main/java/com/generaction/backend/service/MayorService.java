package com.generaction.backend.service;

<<<<<<< HEAD
<<<<<<< HEAD
import com.generaction.backend.dto.MayorDTO;
import com.generaction.backend.entity.Mayor;
import com.generaction.backend.repository.MayorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
=======
=======
import java.io.IOException;
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
<<<<<<< HEAD
=======
import org.springframework.web.multipart.MultipartFile;
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773

import com.generaction.backend.dto.MayorDTO;
import com.generaction.backend.entity.Mayor;
import com.generaction.backend.repository.MayorRepository;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
>>>>>>> mirepo/main

@Service
@RequiredArgsConstructor   // Lombok inyecta el repositorio por constructor
public class MayorService {

<<<<<<< HEAD
=======
    private static final Set<String> NIVELES_VALIDOS = Set.of("alto", "medio", "bajo");
    
>>>>>>> mirepo/main
=======

@Service
@RequiredArgsConstructor   // Lombok inyecta el repository por constructor
public class MayorService {

    private static final Set<String> NIVELES_VALIDOS = Set.of("alto", "medio", "bajo");
    
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
    private final MayorRepository mayorRepository;

    // HU1 — Registrar un mayor
    public Mayor registrarMayor(MayorDTO dto) {
        if (mayorRepository.existsByTelefono(dto.getTelefono())) {
            throw new RuntimeException("Ya existe un mayor con ese teléfono.");
        }
<<<<<<< HEAD

<<<<<<< HEAD
=======
=======
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
        String nivelAutonomia = dto.getNivelAutonomia() == null ? "" : dto.getNivelAutonomia().trim().toLowerCase();
        if (!NIVELES_VALIDOS.contains(nivelAutonomia)) {
            throw new RuntimeException("Nivel de autonomía no válido.");
        }

<<<<<<< HEAD
>>>>>>> mirepo/main
=======
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
        Mayor mayor = new Mayor();
        mayor.setNombre(dto.getNombre());
        mayor.setApellidos(dto.getApellidos());
        mayor.setTelefono(dto.getTelefono());
        mayor.setDireccion(dto.getDireccion());
        mayor.setMunicipio(dto.getMunicipio());
        mayor.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));
<<<<<<< HEAD
<<<<<<< HEAD
        mayor.setNivelAutonomia(dto.getNivelAutonomia());
=======
        mayor.setNivelAutonomia(nivelAutonomia);
>>>>>>> mirepo/main
=======
        mayor.setNivelAutonomia(nivelAutonomia);
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
        mayor.setPreferenciasActividad(dto.getPreferenciasActividad());
        mayor.setContactoFamiliarNombre(dto.getContactoFamiliarNombre());
        mayor.setContactoFamiliarTelefono(dto.getContactoFamiliarTelefono());

        return mayorRepository.save(mayor);
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

    // Dar de baja (no borrar, solo marcar inactivo)
    public void darDeBaja(Long id) {
        Mayor mayor = obtenerPorId(id);
        mayor.setActivo(false);
        mayorRepository.save(mayor);
    }
<<<<<<< HEAD
=======

    // Guardar documento PDF del mayor (HU2)
    public void guardarDocumento(Long id, MultipartFile archivo) throws IOException {
        Mayor mayor = mayorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mayor no encontrado"));

        mayor.setDocumentoPdf(archivo.getBytes());
        mayorRepository.save(mayor);
    }
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
}