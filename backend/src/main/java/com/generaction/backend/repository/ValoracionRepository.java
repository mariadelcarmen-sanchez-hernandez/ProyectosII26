package com.generaction.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generaction.backend.entity.Valoracion;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {

    List<Valoracion> findByVisita_IdVisita(Long idVisita);

    List<Valoracion> findByRol(Valoracion.RolValoracion rol);
}
