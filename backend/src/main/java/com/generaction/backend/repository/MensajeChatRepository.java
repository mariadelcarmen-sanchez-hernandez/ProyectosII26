package com.generaction.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generaction.backend.entity.MensajeChat;

public interface MensajeChatRepository extends JpaRepository<MensajeChat, Long> {
    List<MensajeChat> findByIdVisitaOrderByFechaEnvioAsc(Long idVisita);
    List<MensajeChat> findByIdVisitaAndFechaEnvioAfterOrderByFechaEnvioAsc(Long idVisita, LocalDateTime desde);
    java.util.Optional<MensajeChat> findTopByIdVisitaOrderByFechaEnvioDesc(Long idVisita);
}
