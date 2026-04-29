package com.generaction.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.generaction.backend.entity.Solicitud;
import com.generaction.backend.entity.Solicitud.EstadoSolicitud;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findByEstado(EstadoSolicitud estado);

    List<Solicitud> findByMayor_IdMayor(Long idMayor);

    List<Solicitud> findByEstadoAndMayor_Municipio(EstadoSolicitud estado, String municipio);

    @Query("SELECT s FROM Solicitud s JOIN FETCH s.mayor WHERE s.estado = :estado")
    List<Solicitud> findPendientesConMayor(EstadoSolicitud estado);

    @Query("SELECT s FROM Solicitud s JOIN FETCH s.mayor")
    List<Solicitud> findAllWithMayor();

    long countByEstado(EstadoSolicitud estado);
}