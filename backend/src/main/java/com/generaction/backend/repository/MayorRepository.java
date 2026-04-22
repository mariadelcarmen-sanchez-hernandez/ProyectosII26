package com.generaction.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generaction.backend.entity.Mayor;

@Repository
public interface MayorRepository extends JpaRepository<Mayor, Long> {

    // Spring genera el SQL automáticamente por el nombre del método
    List<Mayor> findByActivoTrue();
    List<Mayor> findByMunicipio(String municipio);
    boolean existsByTelefono(String telefono);

    Optional<Mayor> findByEmail(String email);
    boolean existsByEmail(String email);
}