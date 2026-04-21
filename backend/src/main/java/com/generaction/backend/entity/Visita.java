package com.generaction.backend.entity;

<<<<<<< HEAD
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
=======
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
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773

@Entity
@Table(name = "visitas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visita {

<<<<<<< HEAD
<<<<<<< HEAD
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVisita;

    // Una visita cubre una solicitud concreta
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud", nullable = false, unique = true)
    private Solicitud solicitud;

=======
    public enum EstadoVisita {
        PENDIENTE,
        ASIGNADA,
        EN_TRAMITE,
        REALIZADA,
        COMPLETADA,
        NO_REALIZADA,
        CANCELADA
=======
    public enum EstadoVisita {
        PROGRAMADA,
        REALIZADA,
        NO_REALIZADA
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_visita")
    private Long idVisita;

<<<<<<< HEAD
    // Una visita cubre una solicitud concreta
=======
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud", nullable = false)
    private Solicitud solicitud;

<<<<<<< HEAD
    // El mayor que recibe la visita
=======
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mayor", nullable = false)
    private Mayor mayor;

<<<<<<< HEAD
>>>>>>> mirepo/main
    // El voluntario que acepta y realiza la visita
=======
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_voluntario", nullable = false)
    private Voluntario voluntario;

<<<<<<< HEAD
<<<<<<< HEAD
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
=======
    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDateTime fechaAsignacion = LocalDateTime.now();

    // Se rellena cuando el voluntario confirma que realizó la visita (HU5)
=======
    @Column(name = "fecha_visita", nullable = false)
    private LocalDateTime fechaVisita;

>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
    @Column(name = "duracion_minutos")
    private Integer duracionMinutos;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
<<<<<<< HEAD
    private EstadoVisita estado = EstadoVisita.PENDIENTE;

>>>>>>> mirepo/main
}
=======
    private EstadoVisita estado = EstadoVisita.PROGRAMADA;
}
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
