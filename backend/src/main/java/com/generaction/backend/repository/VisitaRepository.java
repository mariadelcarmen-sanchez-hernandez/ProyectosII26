package com.generaction.backend.repository;

<<<<<<< HEAD
import com.generaction.backend.entity.Visita;
import com.generaction.backend.entity.Visita.EstadoVisita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
=======
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generaction.backend.entity.Visita;
import com.generaction.backend.entity.Visita.EstadoVisita;
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773

@Repository
public interface VisitaRepository extends JpaRepository<Visita, Long> {

<<<<<<< HEAD
=======
    // Visitas de un mayor concreto
    List<Visita> findByMayor_IdMayor(Long idMayor);
    
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
    // Visitas de un voluntario concreto
    List<Visita> findByVoluntario_IdVoluntario(Long idVoluntario);

    // Visitas pendientes de un voluntario (las que tiene que realizar)
    List<Visita> findByVoluntario_IdVoluntarioAndEstado(Long idVoluntario, EstadoVisita estado);
}
