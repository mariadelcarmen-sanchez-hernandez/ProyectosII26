package com.generaction.backend.repository;

import com.generaction.backend.entity.Mayor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MayorRepository extends JpaRepository<Mayor, Long> {

    // Spring genera el SQL automáticamente por el nombre del método
    List<Mayor> findByActivoTrue();
    List<Mayor> findByMunicipio(String municipio);
    boolean existsByTelefono(String telefono);
}