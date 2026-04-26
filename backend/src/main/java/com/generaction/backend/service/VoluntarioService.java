package com.generaction.backend.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.generaction.backend.dto.VoluntarioDTO;
import com.generaction.backend.entity.MovimientoWallet;
import com.generaction.backend.entity.Voluntario;
import com.generaction.backend.repository.MovimientoWalletRepository;
import com.generaction.backend.repository.VoluntarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoluntarioService {

    private final VoluntarioRepository voluntarioRepository;
    private final MovimientoWalletRepository movimientoWalletRepository;
    private final DniValidationService dniValidationService;

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

        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voluntario no encontrado"));

        voluntario.setDocumentoPdf(archivo.getBytes());
        voluntarioRepository.save(voluntario);
    }

    public Voluntario sumarPuntos(Long id, Integer puntos) {
        if (puntos == null || puntos <= 0) {
            throw new RuntimeException("La cantidad de puntos debe ser mayor que 0.");
        }

        Voluntario voluntario = obtenerPorId(id);
        int saldoActual = voluntario.getPuntosWallet() != null ? voluntario.getPuntosWallet() : 0;
        voluntario.setPuntosWallet(saldoActual + puntos);

        Voluntario actualizado = voluntarioRepository.save(voluntario);

        guardarMovimiento(actualizado.getIdVoluntario(), puntos, "SUMA", "Carga de puntos");

        return actualizado;
    }

    public Voluntario restarPuntos(Long id, Integer puntos) {
        if (puntos == null || puntos <= 0) {
            throw new RuntimeException("La cantidad de puntos debe ser mayor que 0.");
        }

        Voluntario voluntario = obtenerPorId(id);
        int saldoActual = voluntario.getPuntosWallet() != null ? voluntario.getPuntosWallet() : 0;

        if (saldoActual < puntos) {
            throw new RuntimeException("Saldo insuficiente en la wallet.");
        }

        voluntario.setPuntosWallet(saldoActual - puntos);

        Voluntario actualizado = voluntarioRepository.save(voluntario);

        guardarMovimiento(actualizado.getIdVoluntario(), puntos, "RESTA", "Descuento de puntos");

        return actualizado;
    }

    public List<MovimientoWallet> obtenerMovimientosWallet(Long idVoluntario) {
        obtenerPorId(idVoluntario);
        return movimientoWalletRepository.findByVoluntarioIdOrderByFechaDesc(idVoluntario);
    }

    private void guardarMovimiento(Long voluntarioId, Integer puntos, String tipo, String motivo) {
        MovimientoWallet movimiento = new MovimientoWallet();
        movimiento.setVoluntarioId(voluntarioId);
        movimiento.setPuntos(puntos);
        movimiento.setTipo(tipo);
        movimiento.setMotivo(motivo);
        movimiento.setFecha(LocalDateTime.now());

        movimientoWalletRepository.save(movimiento);
    }
}