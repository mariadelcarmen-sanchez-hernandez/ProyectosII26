package com.generaction.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "visitas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVisita;

    // Una visita cubre una solicitud concreta
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud", nullable = false, unique = true)
    private Solicitud solicitud;

    // El voluntario que acepta y realiza la visita
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_voluntario", nullable = false)
    private Voluntario voluntario;

    @Column(nullable = false)
    private LocalDateTime fechaAsignacion = LocalDateTime.now();

    // Se rellena cuando el voluntario confirma que realizó la visita (HU5)
    private LocalDate fechaRealizacion;

    private Integer duracionMinutos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoVisita estado = EstadoVisita.PENDIENTE;

    public enum EstadoVisita {
        PENDIENTE, REALIZADA, CANCELADA
    }
}
