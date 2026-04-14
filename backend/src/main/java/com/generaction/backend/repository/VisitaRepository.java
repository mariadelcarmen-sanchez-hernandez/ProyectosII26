package com.generaction.backend.repository;

import com.generaction.backend.entity.Visita;
import com.generaction.backend.entity.Visita.EstadoVisita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitaRepository extends JpaRepository<Visita, Long> {

    // Visitas de un voluntario concreto
    List<Visita> findByVoluntario_IdVoluntario(Long idVoluntario);

    // Visitas pendientes de un voluntario (las que tiene que realizar)
    List<Visita> findByVoluntario_IdVoluntarioAndEstado(Long idVoluntario, EstadoVisita estado);
}
