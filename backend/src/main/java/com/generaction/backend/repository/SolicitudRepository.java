package com.generaction.backend.repository;

<<<<<<< HEAD
import com.generaction.backend.entity.Solicitud;
import com.generaction.backend.entity.Solicitud.EstadoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
=======
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generaction.backend.entity.Solicitud;
import com.generaction.backend.entity.Solicitud.EstadoSolicitud;
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    // Todas las solicitudes pendientes (las que puede ver un voluntario)
    List<Solicitud> findByEstado(EstadoSolicitud estado);

    // Solicitudes de un mayor concreto
    List<Solicitud> findByMayor_IdMayor(Long idMayor);

    // Solicitudes pendientes de un municipio concreto
    List<Solicitud> findByEstadoAndMayor_Municipio(EstadoSolicitud estado, String municipio);
}