package com.generaction.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.generaction.backend.dto.EstadoVisitaDTO;
import com.generaction.backend.dto.NotificacionDTO;
import com.generaction.backend.dto.RegistroVisitaDTO;
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

}