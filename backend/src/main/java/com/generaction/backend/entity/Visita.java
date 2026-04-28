package com.generaction.backend.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
        PROGRAMADA,
        REALIZADA,
        NO_REALIZADA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_visita")
    private Long idVisita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Solicitud solicitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mayor", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Mayor mayor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_voluntario", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Voluntario voluntario;

    @Column(name = "fecha_visita", nullable = false)
    private LocalDateTime fechaVisita;

    @Column(name = "duracion_minutos")
    private Integer duracionMinutos;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoVisita estado = EstadoVisita.PROGRAMADA;
}