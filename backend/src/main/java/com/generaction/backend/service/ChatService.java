package com.generaction.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.generaction.backend.dto.MensajeChatDTO;
import com.generaction.backend.entity.MensajeChat;
import com.generaction.backend.repository.MensajeChatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MensajeChatRepository mensajeChatRepository;

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
