package com.generaction.backend.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generaction.backend.dto.ConversacionResumenDTO;
import com.generaction.backend.dto.MensajeChatDTO;
import com.generaction.backend.entity.MensajeChat;
import com.generaction.backend.entity.Visita;
import com.generaction.backend.repository.MensajeChatRepository;
import com.generaction.backend.repository.VisitaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MensajeChatRepository mensajeChatRepository;
    private final VisitaRepository visitaRepository;

    public MensajeChatDTO enviarMensaje(MensajeChatDTO dto) {
        MensajeChat mensaje = new MensajeChat();
        mensaje.setIdVisita(dto.getIdVisita());
        mensaje.setRolEmisor(MensajeChat.RolEmisor.valueOf(dto.getRolEmisor()));
        mensaje.setIdEmisor(dto.getIdEmisor());
        mensaje.setNombreEmisor(dto.getNombreEmisor());
        mensaje.setContenido(dto.getContenido());
        mensaje.setFechaEnvio(LocalDateTime.now());
        return toDTO(mensajeChatRepository.save(mensaje));
    }

    public List<MensajeChatDTO> obtenerMensajesPorVisita(Long idVisita) {
        return mensajeChatRepository.findByIdVisitaOrderByFechaEnvioAsc(idVisita)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<MensajeChatDTO> obtenerMensajesNuevos(Long idVisita, LocalDateTime desde) {
        return mensajeChatRepository.findByIdVisitaAndFechaEnvioAfterOrderByFechaEnvioAsc(idVisita, desde)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConversacionResumenDTO> obtenerConversacionesMayor(Long idMayor) {
        return visitaRepository.findByMayor_IdMayor(idMayor).stream()
                .map(v -> toConversacion(v, nombreVoluntario(v)))
                .sorted(Comparator.comparing(
                        ConversacionResumenDTO::getFechaUltimoMensaje,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConversacionResumenDTO> obtenerConversacionesVoluntario(Long idVoluntario) {
        return visitaRepository.findByVoluntario_IdVoluntario(idVoluntario).stream()
                .map(v -> toConversacion(v, nombreMayor(v)))
                .sorted(Comparator.comparing(
                        ConversacionResumenDTO::getFechaUltimoMensaje,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    private ConversacionResumenDTO toConversacion(Visita v, String nombreContacto) {
        Optional<MensajeChat> ultimo =
                mensajeChatRepository.findTopByIdVisitaOrderByFechaEnvioDesc(v.getIdVisita());
        String actividad = v.getSolicitud() != null ? v.getSolicitud().getTipoActividad() : "Actividad";
        return new ConversacionResumenDTO(
                v.getIdVisita(),
                nombreContacto,
                actividad,
                ultimo.map(MensajeChat::getContenido).orElse(null),
                ultimo.map(MensajeChat::getFechaEnvio).orElse(null)
        );
    }

    private String nombreVoluntario(Visita v) {
        if (v.getVoluntario() == null) return "Voluntario";
        return (v.getVoluntario().getNombre() + " " + v.getVoluntario().getApellidos()).trim();
    }

    private String nombreMayor(Visita v) {
        if (v.getMayor() == null) return "Persona mayor";
        return (v.getMayor().getNombre() + " " + v.getMayor().getApellidos()).trim();
    }

    private MensajeChatDTO toDTO(MensajeChat m) {
        return new MensajeChatDTO(
                m.getIdMensaje(),
                m.getIdVisita(),
                m.getRolEmisor().name(),
                m.getIdEmisor(),
                m.getNombreEmisor(),
                m.getContenido(),
                m.getFechaEnvio()
        );
    }
}
