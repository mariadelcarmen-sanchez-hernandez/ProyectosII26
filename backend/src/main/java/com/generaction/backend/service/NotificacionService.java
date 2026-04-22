package com.generaction.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.generaction.backend.dto.NotificacionDTO;
import com.generaction.backend.entity.Notificacion;
import com.generaction.backend.repository.NotificacionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionDTO crearNotificacion(NotificacionDTO dto) {
        Notificacion notificacion = new Notificacion();
        notificacion.setIdMayor(dto.getIdMayor());
        notificacion.setIdVisita(dto.getIdVisita());
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setFecha(dto.getFecha() != null ? dto.getFecha() : LocalDateTime.now());
        notificacion.setLeida(dto.getLeida() != null ? dto.getLeida() : false);

        Notificacion guardada = notificacionRepository.save(notificacion);
        return toDTO(guardada);
    }

    public List<NotificacionDTO> obtenerNotificacionesPorMayor(Long idMayor) {
        return notificacionRepository.findByIdMayorOrderByFechaDesc(idMayor)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<NotificacionDTO> obtenerNoLeidasPorMayor(Long idMayor) {
        return notificacionRepository.findByIdMayorAndLeidaFalseOrderByFechaDesc(idMayor)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public NotificacionDTO marcarComoLeida(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        notificacion.setLeida(true);
        return toDTO(notificacionRepository.save(notificacion));
    }

    public void eliminarNotificacion(Long id) {
        if (!notificacionRepository.existsById(id)) {
            throw new RuntimeException("Notificación no encontrada");
        }
        notificacionRepository.deleteById(id);
    }

    private NotificacionDTO toDTO(Notificacion notificacion) {
        NotificacionDTO dto = new NotificacionDTO();
        dto.setId(notificacion.getId());
        dto.setIdMayor(notificacion.getIdMayor());
        dto.setIdVisita(notificacion.getIdVisita());
        dto.setMensaje(notificacion.getMensaje());
        dto.setFecha(notificacion.getFecha());
        dto.setLeida(notificacion.getLeida());
        return dto;
    }
}