package com.generaction.backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "visitas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visita {

    public enum EstadoVisita {
        PENDIENTE,
        ASIGNADA,
        EN_TRAMITE,
        REALIZADA,
        COMPLETADA,
        NO_REALIZADA,
        CANCELADA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_visita")
    private Long idVisita;

    // Una visita cubre una solicitud concreta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud", nullable = false)
    private Solicitud solicitud;

    // El mayor que recibe la visita
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mayor", nullable = false)
    private Mayor mayor;

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
}
