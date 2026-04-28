package com.generaction.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.generaction.backend.dto.EstadoVisitaDTO;
import com.generaction.backend.dto.MayorResumenDTO;
import com.generaction.backend.dto.NotificacionDTO;
import com.generaction.backend.dto.RegistroVisitaDTO;
import com.generaction.backend.dto.SolicitudResumenDTO;
import com.generaction.backend.dto.VisitaResponseDTO;
import com.generaction.backend.dto.VoluntarioResumenDTO;
import com.generaction.backend.entity.Mayor;
import com.generaction.backend.entity.Visita;
import com.generaction.backend.repository.VisitaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisitaService {

    private final VisitaRepository visitaRepository;
    
    private final NotificacionService notificacionService;

    public List<Visita> obtenerTodas() {
        return visitaRepository.findAll();
    }

    public Visita obtenerPorId(Long id) {
        return visitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visita no encontrada: " + id));
    }

    public List<Visita> obtenerPorMayor(Long idMayor) {
        return visitaRepository.findByMayor_IdMayor(idMayor);
    }

    public List<Visita> obtenerPorVoluntario(Long idVoluntario) {
        return visitaRepository.findByVoluntario_IdVoluntario(idVoluntario);
    }

    public List<Visita> obtenerProgramadasPorVoluntario(Long idVoluntario) {
        return visitaRepository.findByVoluntario_IdVoluntarioAndEstado(
                idVoluntario,
                Visita.EstadoVisita.PROGRAMADA
        );
    }

    public Visita registrarRealizacion(Long idVisita, RegistroVisitaDTO dto) {
        Visita visita = visitaRepository.findById(idVisita)
                .orElseThrow(() -> new RuntimeException("Visita no encontrada: " + idVisita));

        visita.setFechaVisita(LocalDateTime.parse(dto.getFechaVisita()));
        visita.setDuracionMinutos(dto.getDuracionMinutos());
        visita.setEstado(Visita.EstadoVisita.REALIZADA);

        Visita visitaGuardada = visitaRepository.save(visita);

        Mayor mayor = visitaGuardada.getMayor();

        if (mayor != null && mayor.getContactoFamiliarTelefono() != null
                && !mayor.getContactoFamiliarTelefono().isBlank()) {

            NotificacionDTO notificacionDTO = new NotificacionDTO();
            notificacionDTO.setIdMayor(mayor.getIdMayor());
            notificacionDTO.setIdVisita(visitaGuardada.getIdVisita());
            notificacionDTO.setMensaje("El voluntario ha llegado a la visita.");
            notificacionDTO.setFecha(LocalDateTime.now());
            notificacionDTO.setLeida(false);

            notificacionService.crearNotificacion(notificacionDTO);
        }

        return visitaGuardada;
    }

    public Visita marcarNoRealizada(Long idVisita) {
        Visita visita = visitaRepository.findById(idVisita)
                .orElseThrow(() -> new RuntimeException("Visita no encontrada: " + idVisita));

        visita.setEstado(Visita.EstadoVisita.NO_REALIZADA);
        return visitaRepository.save(visita);
    }

    public Visita actualizarEstado(Long idVisita, EstadoVisitaDTO dto) {
        Visita visita = visitaRepository.findById(idVisita)
                .orElseThrow(() -> new RuntimeException("Visita no encontrada: " + idVisita));

        visita.setEstado(dto.getEstado());
        return visitaRepository.save(visita);
    }

    public VisitaResponseDTO convertirADTO(Visita visita) {
        SolicitudResumenDTO solicitudDTO = new SolicitudResumenDTO(
                visita.getSolicitud().getIdSolicitud(),
                visita.getSolicitud().getTipoActividad(),
                visita.getSolicitud().getDescripcion(),
                visita.getSolicitud().getFechaSolicitada(),
                visita.getSolicitud().getHorario(),
                visita.getSolicitud().getEstado().name(),
                visita.getSolicitud().getFechaCreacion()
        );

        MayorResumenDTO mayorDTO = new MayorResumenDTO(
                visita.getMayor().getIdMayor(),
                visita.getMayor().getNombre(),
                visita.getMayor().getApellidos(),
                visita.getMayor().getMunicipio()
        );

        VoluntarioResumenDTO voluntarioDTO = new VoluntarioResumenDTO(
                visita.getVoluntario().getIdVoluntario(),
                visita.getVoluntario().getNombre(),
                visita.getVoluntario().getApellidos(),
                visita.getVoluntario().getMunicipio(),
                visita.getVoluntario().getPuntosWallet()
        );

        return new VisitaResponseDTO(
                visita.getIdVisita(),
                solicitudDTO,
                mayorDTO,
                voluntarioDTO,
                visita.getFechaVisita(),
                visita.getDuracionMinutos(),
                visita.getEstado().name()
        );
    }

    public List<VisitaResponseDTO> obtenerPorVoluntarioDTO(Long idVoluntario) {
        return obtenerPorVoluntario(idVoluntario)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public VisitaResponseDTO actualizarEstadoDTO(Long idVisita, EstadoVisitaDTO dto) {
        Visita visita = actualizarEstado(idVisita, dto);
        return convertirADTO(visita);
    }

}