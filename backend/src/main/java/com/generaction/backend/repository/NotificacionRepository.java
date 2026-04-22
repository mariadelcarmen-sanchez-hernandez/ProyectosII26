package com.generaction.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generaction.backend.entity.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByIdMayorOrderByFechaDesc(Long idMayor);

    List<Notificacion> findByIdMayorAndLeidaFalseOrderByFechaDesc(Long idMayor);
}