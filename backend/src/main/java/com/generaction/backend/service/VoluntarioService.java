package com.generaction.backend.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.generaction.backend.dto.VoluntarioDTO;
import com.generaction.backend.entity.Voluntario;
import com.generaction.backend.repository.VoluntarioRepository;

import lombok.RequiredArgsConstructor;

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
        voluntario.setTelefono(dto.getTelefono());
        voluntario.setDireccion(dto.getDireccion() != null ? dto.getDireccion() : "");
        voluntario.setMunicipio(dto.getMunicipio());
        voluntario.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));
        voluntario.setDisponibilidad(dto.getDisponibilidad());
        voluntario.setPuntosWallet(0);
        voluntario.setPasswordHash(dto.getPassword());

        return voluntarioRepository.save(voluntario);
    }

    public List<Voluntario> obtenerVoluntariosActivos() {
        return voluntarioRepository.findByActivoTrue();
    }

    public Voluntario obtenerPorId(Long id) {
        return voluntarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voluntario no encontrado con id: " + id));
    }

    // Guardar documento PDF del voluntario (HU2)
    public void guardarDocumento(Long id, MultipartFile archivo) throws IOException {
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voluntario no encontrado"));

        voluntario.setDocumentoPdf(archivo.getBytes());
        voluntarioRepository.save(voluntario);
    }
}