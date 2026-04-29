package com.generaction.backend.controller;

import com.generaction.backend.entity.Valoracion;
import com.generaction.backend.repository.MayorRepository;
import com.generaction.backend.repository.SolicitudRepository;
import com.generaction.backend.repository.ValoracionRepository;
import com.generaction.backend.repository.VoluntarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Map<String, Object> getStats() {
        var mayores = mayorRepository.findAll();
        var voluntarios = voluntarioRepository.findAll();
        var solicitudes = solicitudRepository.findAll();
        var resenas = valoracionRepository.findAll();

        Map<String, Long> solicitudesPorEstado = solicitudes.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getEstado().name(),
                        Collectors.counting()
                ));

        Map<String, Object> response = new HashMap<>();
        response.put("totalMayores", mayores.size());
        response.put("totalVoluntarios", voluntarios.size());
        response.put("totalSolicitudes", solicitudes.size());
        response.put("totalResenas", resenas.size());
        response.put("solicitudesPorEstado", solicitudesPorEstado);

        return response;
    }

    @GetMapping("/usuarios/mayores")
    public List<Map<String, Object>> getMayores() {
        return mayorRepository.findAll().stream().map(m -> {
            Map<String, Object> item = new HashMap<>();
            item.put("nombre", m.getNombre() + " " + m.getApellidos());
            item.put("email", m.getEmail());
            item.put("municipio", m.getMunicipio());
            item.put("activo", m.getActivo());
            return item;
        }).collect(Collectors.toList());
    }

    @GetMapping("/usuarios/voluntarios")
    public List<Map<String, Object>> getVoluntarios() {
        return voluntarioRepository.findAll().stream().map(v -> {
            Map<String, Object> item = new HashMap<>();
            item.put("nombre", v.getNombre() + " " + v.getApellidos());
            item.put("email", v.getEmail());
            item.put("municipio", v.getMunicipio());
            item.put("puntos", v.getPuntosWallet());
            item.put("verificado", v.getEstadoVerificacion() != null ? v.getEstadoVerificacion() : "pendiente");
            return item;
        }).collect(Collectors.toList());
    }

    @GetMapping("/solicitudes")
    public List<Map<String, Object>> getSolicitudes() {
        return solicitudRepository.findAll().stream().map(s -> {
            Map<String, Object> item = new HashMap<>();
            item.put("tipoActividad", s.getTipoActividad());
            item.put("estado", s.getEstado().name());
            item.put("descripcion", s.getDescripcion());
            item.put("fechaSolicitada", s.getFechaSolicitada() != null ? s.getFechaSolicitada().toString() : "");
            item.put("horario", s.getHorario() != null ? s.getHorario() : "");
            item.put("mayor", s.getMayor() != null
                    ? s.getMayor().getNombre() + " " + s.getMayor().getApellidos()
                    : "Sin mayor");
            return item;
        }).collect(Collectors.toList());
    }

    @GetMapping("/resenas")
    public List<Map<String, Object>> getResenas() {
        return valoracionRepository.findAll().stream().map(r -> {
            Map<String, Object> item = new HashMap<>();

            String autor = "Usuario";
            if (r.getVisita() != null) {
                if (r.getRol() == Valoracion.RolValoracion.MAYOR
                        && r.getVisita().getMayor() != null) {
                    autor = r.getVisita().getMayor().getNombre() + " " + r.getVisita().getMayor().getApellidos();
                } else if (r.getRol() == Valoracion.RolValoracion.VOLUNTARIO
                        && r.getVisita().getVoluntario() != null) {
                    autor = r.getVisita().getVoluntario().getNombre() + " " + r.getVisita().getVoluntario().getApellidos();
                }
            }

            item.put("autor", autor);
            item.put("comentario", r.getComentario() != null ? r.getComentario() : "");
            item.put("puntuacion", r.getPuntuacion());
            item.put("fecha", r.getFechaValoracion() != null ? r.getFechaValoracion().toString() : "");
            item.put("rol", r.getRol() != null ? r.getRol().name() : "MAYOR");

            return item;
        }).collect(Collectors.toList());
    }
}