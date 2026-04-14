package com.generaction.backend.repository;

import com.generaction.backend.entity.Solicitud;
import com.generaction.backend.entity.Solicitud.EstadoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    // Todas las solicitudes pendientes (las que puede ver un voluntario)
    List<Solicitud> findByEstado(EstadoSolicitud estado);

    // Solicitudes de un mayor concreto
    List<Solicitud> findByMayor_IdMayor(Long idMayor);

    // Solicitudes pendientes de un municipio concreto
    List<Solicitud> findByEstadoAndMayor_Municipio(EstadoSolicitud estado, String municipio);
}