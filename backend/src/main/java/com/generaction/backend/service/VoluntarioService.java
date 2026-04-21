package com.generaction.backend.service;

<<<<<<< HEAD
import com.generaction.backend.dto.VoluntarioDTO;
import com.generaction.backend.entity.Voluntario;
import com.generaction.backend.repository.VoluntarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
=======
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.generaction.backend.dto.VoluntarioDTO;
import com.generaction.backend.entity.Voluntario;
import com.generaction.backend.repository.VoluntarioRepository;

import lombok.RequiredArgsConstructor;
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773

@Service
@RequiredArgsConstructor
public class VoluntarioService {

    private final VoluntarioRepository voluntarioRepository;

    // HU2 — Registrar un voluntario
    public Voluntario registrarVoluntario(VoluntarioDTO dto) {
        if (voluntarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Ya existe un voluntario con ese email.");
        }

        Voluntario voluntario = new Voluntario();
        voluntario.setNombre(dto.getNombre());
        voluntario.setApellidos(dto.getApellidos());
        voluntario.setEmail(dto.getEmail());
<<<<<<< HEAD
=======
        voluntario.setPassword(dto.getPassword()); // En un caso real, se debería encriptar la contraseña
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
        voluntario.setTelefono(dto.getTelefono());
        voluntario.setDireccion(dto.getDireccion() != null ? dto.getDireccion() : "");
        voluntario.setMunicipio(dto.getMunicipio());
        voluntario.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));
        voluntario.setDisponibilidad(dto.getDisponibilidad());
        voluntario.setPuntosWallet(0);

        return voluntarioRepository.save(voluntario);
    }

    public List<Voluntario> obtenerVoluntariosActivos() {
        return voluntarioRepository.findByActivoTrue();
    }

    public Voluntario obtenerPorId(Long id) {
        return voluntarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Voluntario no encontrado con id: " + id));
    }
<<<<<<< HEAD
=======

    // Guardar documento PDF del voluntario (HU2)
    public void guardarDocumento(Long id, MultipartFile archivo) throws IOException {
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voluntario no encontrado"));

        voluntario.setDocumentoPdf(archivo.getBytes());
        voluntarioRepository.save(voluntario);
    }
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
}
