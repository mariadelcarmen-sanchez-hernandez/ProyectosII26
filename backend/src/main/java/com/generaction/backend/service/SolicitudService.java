package com.generaction.backend.service;

<<<<<<< HEAD
import com.generaction.backend.dto.AsignacionDTO;
import com.generaction.backend.dto.SolicitudDTO;
import com.generaction.backend.entity.*;
import com.generaction.backend.entity.Solicitud.EstadoSolicitud;
import com.generaction.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

=======
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generaction.backend.dto.AsignacionDTO;
import com.generaction.backend.dto.SolicitudDTO;
import com.generaction.backend.entity.Mayor;
import com.generaction.backend.entity.Solicitud;
import com.generaction.backend.entity.Solicitud.EstadoSolicitud;
import com.generaction.backend.entity.Visita;
import com.generaction.backend.entity.Voluntario;
import com.generaction.backend.repository.MayorRepository;
import com.generaction.backend.repository.SolicitudRepository;
import com.generaction.backend.repository.VisitaRepository;
import com.generaction.backend.repository.VoluntarioRepository;

import lombok.RequiredArgsConstructor;

>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
@Service
@RequiredArgsConstructor
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final VisitaRepository    visitaRepository;
    private final MayorRepository     mayorRepository;
    private final VoluntarioRepository voluntarioRepository;

    // HU3 — Crear solicitud de acompañamiento
    public Solicitud crearSolicitud(SolicitudDTO dto) {
        Mayor mayor = mayorRepository.findById(dto.getIdMayor())
            .orElseThrow(() -> new RuntimeException("Mayor no encontrado: " + dto.getIdMayor()));

        Solicitud solicitud = new Solicitud();
        solicitud.setMayor(mayor);
        solicitud.setTipoActividad(dto.getTipoActividad());
        solicitud.setFechaSolicitada(LocalDate.parse(dto.getFechaSolicitada()));
        solicitud.setHorario(dto.getHorario());
        solicitud.setDescripcion(dto.getDescripcion());
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);

        return solicitudRepository.save(solicitud);
    }

<<<<<<< HEAD
=======
    public List<Solicitud> obtenerPorMayor(Long idMayor) {
        return solicitudRepository.findByMayor_IdMayor(idMayor);
    }

>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
    // Ver todas las solicitudes pendientes (las que puede aceptar un voluntario)
    public List<Solicitud> obtenerPendientes() {
        return solicitudRepository.findByEstado(EstadoSolicitud.PENDIENTE);
    }

    // Ver solicitudes pendientes filtradas por municipio (HU4 — cercanía)
    public List<Solicitud> obtenerPendientesPorMunicipio(String municipio) {
        return solicitudRepository.findByEstadoAndMayor_Municipio(
            EstadoSolicitud.PENDIENTE, municipio
        );
    }

    // HU4 — Voluntario acepta una solicitud → se crea la Visita
    @Transactional
    public Visita asignarVisita(AsignacionDTO dto) {
        Solicitud solicitud = solicitudRepository.findById(dto.getIdSolicitud())
<<<<<<< HEAD
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada: " + dto.getIdSolicitud()));
=======
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada: " + dto.getIdSolicitud()));
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773

        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new RuntimeException("Esta solicitud ya no está disponible.");
        }

        Voluntario voluntario = voluntarioRepository.findById(dto.getIdVoluntario())
<<<<<<< HEAD
            .orElseThrow(() -> new RuntimeException("Voluntario no encontrado: " + dto.getIdVoluntario()));

        // Marcar solicitud como asignada
        solicitud.setEstado(EstadoSolicitud.ASIGNADA);
        solicitudRepository.save(solicitud);

        // Crear la visita
        Visita visita = new Visita();
        visita.setSolicitud(solicitud);
        visita.setVoluntario(voluntario);
        visita.setEstado(Visita.EstadoVisita.PENDIENTE);
=======
                .orElseThrow(() -> new RuntimeException("Voluntario no encontrado: " + dto.getIdVoluntario()));

        solicitud.setEstado(EstadoSolicitud.ASIGNADA);
        solicitudRepository.save(solicitud);

        Visita visita = new Visita();
        visita.setSolicitud(solicitud);
        visita.setMayor(solicitud.getMayor());
        visita.setVoluntario(voluntario);
        visita.setFechaVisita(solicitud.getFechaSolicitada().atStartOfDay());
        visita.setDuracionMinutos(null);
        visita.setEstado(Visita.EstadoVisita.PROGRAMADA);
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773

        return visitaRepository.save(visita);
    }

<<<<<<< HEAD
    // Ver todas las solicitudes de un mayor
    public List<Solicitud> obtenerPorMayor(Long idMayor) {
        return solicitudRepository.findByMayor_IdMayor(idMayor);
    }
=======
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
}