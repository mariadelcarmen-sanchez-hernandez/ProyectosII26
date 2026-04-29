package com.generaction.backend.service;

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

@Service
@RequiredArgsConstructor
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final VisitaRepository visitaRepository;
    private final MayorRepository mayorRepository;
    private final VoluntarioRepository voluntarioRepository;

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

    public List<Solicitud> obtenerPorMayor(Long idMayor) {
        return solicitudRepository.findByMayor_IdMayor(idMayor);
    }

    public List<Solicitud> obtenerPendientes() {
        return solicitudRepository.findPendientesConMayor(EstadoSolicitud.PENDIENTE);
    }

    public List<Solicitud> obtenerPendientesPorMunicipio(String municipio) {
        return solicitudRepository.findByEstadoAndMayor_Municipio(
                EstadoSolicitud.PENDIENTE, municipio
        );
    }

    @Transactional
    public Visita asignarVisita(AsignacionDTO dto) {
        Solicitud solicitud = solicitudRepository.findById(dto.getIdSolicitud())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada: " + dto.getIdSolicitud()));

        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new RuntimeException("Esta solicitud ya no está disponible.");
        }

        Voluntario voluntario = voluntarioRepository.findById(dto.getIdVoluntario())
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

        return visitaRepository.save(visita);
    }
}