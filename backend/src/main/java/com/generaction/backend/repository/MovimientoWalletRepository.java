package com.generaction.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generaction.backend.entity.MovimientoWallet;

public interface MovimientoWalletRepository extends JpaRepository<MovimientoWallet, Long> {
    List<MovimientoWallet> findByVoluntarioIdOrderByFechaDesc(Long voluntarioId);
}