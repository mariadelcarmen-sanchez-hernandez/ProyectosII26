package com.generaction.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generaction.backend.entity.Visita;
import com.generaction.backend.entity.Visita.EstadoVisita;

@Repository
public interface VisitaRepository extends JpaRepository<Visita, Long> {

    // Visitas de un mayor concreto
    List<Visita> findByMayor_IdMayor(Long idMayor);
    
    // Visitas de un voluntario concreto
    List<Visita> findByVoluntario_IdVoluntario(Long idVoluntario);

    // Visitas pendientes de un voluntario (las que tiene que realizar)
    List<Visita> findByVoluntario_IdVoluntarioAndEstado(Long idVoluntario, EstadoVisita estado);
}
