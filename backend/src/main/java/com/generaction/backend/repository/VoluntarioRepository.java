package com.generaction.backend.repository;

import com.generaction.backend.entity.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {

    Optional<Voluntario> findByEmail(String email);
    List<Voluntario> findByActivoTrue();
    List<Voluntario> findByMunicipio(String municipio);
    boolean existsByEmail(String email);
}