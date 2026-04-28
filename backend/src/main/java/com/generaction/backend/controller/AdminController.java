package com.generaction.backend.controller;

import com.generaction.backend.entity.Solicitud;
import com.generaction.backend.entity.Valoracion;
import com.generaction.backend.repository.MayorRepository;
import com.generaction.backend.repository.SolicitudRepository;
import com.generaction.backend.repository.ValoracionRepository;
import com.generaction.backend.repository.VoluntarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AdminController {

    private final MayorRepository mayorRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final SolicitudRepository solicitudRepository;
    private final ValoracionRepository valoracionRepository;

    @GetMapping("/stats")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getStats() {
        long totalMayores = mayorRepository.count();
        long totalVoluntarios = voluntarioRepository.count();
        long totalSolicitudes = solicitudRepository.count();
        long pendientes = solicitudRepository.countByEstado(Solicitud.EstadoSolicitud.PENDIENTE);
        long asignadas = solicitudRepository.countByEstado(Solicitud.EstadoSolicitud.ASIGNADA);
        long enTramite = solicitudRepository.countByEstado(Solicitud.EstadoSolicitud.EN_TRAMITE);
        long completadas = solicitudRepository.countByEstado(Solicitud.EstadoSolicitud.COMPLETADA);
        long canceladas = solicitudRepository.countByEstado(Solicitud.EstadoSolicitud.CANCELADA);
        long totalResenas = valoracionRepository.count();

        return ResponseEntity.ok(Map.of(
            "totalMayores", totalMayores,
            "totalVoluntarios", totalVoluntarios,
            "totalSolicitudes", totalSolicitudes,
            "solicitudesPorEstado", Map.of(
                "PENDIENTE", pendientes,
                "ASIGNADA", asignadas,
                "EN_TRAMITE", enTramite,
                "COMPLETADA", completadas,
                "CANCELADA", canceladas
            ),
            "totalResenas", totalResenas
        ));
    }

    @GetMapping("/usuarios/mayores")
    public ResponseEntity<?> getMayores() {
        var mayores = mayorRepository.findAll().stream().map(m -> Map.<String, Object>of(
            "id", m.getIdMayor(),
            "nombre", m.getNombre() + " " + m.getApellidos(),
            "email", m.getEmail(),
            "municipio", m.getMunicipio(),
            "telefono", m.getTelefono(),
            "activo", m.getActivo()
        )).toList();
        return ResponseEntity.ok(mayores);
    }

    @GetMapping("/usuarios/voluntarios")
    public ResponseEntity<?> getVoluntarios() {
        var voluntarios = voluntarioRepository.findAll().stream().map(v -> Map.<String, Object>of(
            "id", v.getIdVoluntario(),
            "nombre", v.getNombre() + " " + v.getApellidos(),
            "email", v.getEmail(),
            "municipio", v.getMunicipio(),
            "telefono", v.getTelefono(),
            "activo", v.getActivo(),
            "verificado", v.getEstadoVerificacion(),
            "puntos", v.getPuntosWallet()
        )).toList();
        return ResponseEntity.ok(voluntarios);
    }

    @GetMapping("/solicitudes")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getSolicitudes() {
        var solicitudes = solicitudRepository.findAllWithMayor().stream().map(s -> Map.<String, Object>of(
            "id", s.getIdSolicitud(),
            "mayor", s.getMayor().getNombre() + " " + s.getMayor().getApellidos(),
            "tipoActividad", s.getTipoActividad(),
            "descripcion", s.getDescripcion() != null ? s.getDescripcion() : "",
            "fechaSolicitada", s.getFechaSolicitada().toString(),
            "horario", s.getHorario(),
            "estado", s.getEstado().name(),
            "fechaCreacion", s.getFechaCreacion().toString()
        )).toList();
        return ResponseEntity.ok(solicitudes);
    }

    @GetMapping("/resenas")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getResenas() {
        var resenas = valoracionRepository.findAllWithDetails().stream().map(v -> Map.<String, Object>of(
            "id", v.getIdValoracion(),
            "autor", v.getRol() == Valoracion.RolValoracion.MAYOR
                ? v.getVisita().getMayor().getNombre() + " " + v.getVisita().getMayor().getApellidos()
                : v.getVisita().getVoluntario().getNombre() + " " + v.getVisita().getVoluntario().getApellidos(),
            "rol", v.getRol().name(),
            "puntuacion", v.getPuntuacion(),
            "comentario", v.getComentario() != null ? v.getComentario() : "",
            "fecha", v.getFechaValoracion().toString()
        )).toList();
        return ResponseEntity.ok(resenas);
    }
}
