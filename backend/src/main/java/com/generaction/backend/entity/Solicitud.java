package com.generaction.backend.entity;

<<<<<<< HEAD
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

=======
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

>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
@Entity
@Table(name = "solicitudes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {

<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
    // Enum interno — los estados posibles de una solicitud
    public enum EstadoSolicitud {
        PENDIENTE,
        ASIGNADA,
        EN_TRAMITE,
        COMPLETADA,
        CANCELADA
    }

<<<<<<< HEAD
>>>>>>> mirepo/main
=======
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitud;

    // Relación con Mayor: una solicitud pertenece a un mayor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mayor", nullable = false)
    private Mayor mayor;

    // Tipo de actividad: paseo, medico, compras, conversacion
<<<<<<< HEAD
<<<<<<< HEAD
    @Column(nullable = false, length = 50)
    private String tipoActividad;

    @Column(nullable = false)
    private LocalDate fechaSolicitada;

    // "manana" o "tarde"
    @Column(nullable = false, length = 20)
=======
    @Column(name = "tipo_actividad", nullable = false, length = 50)
    private String tipoActividad;

=======
    @Column(nullable = false, length = 50)
    private String tipoActividad;
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
    @Column(name = "fecha_solicitada", nullable = false)
    private LocalDate fechaSolicitada;

    // "manana" o "tarde"
    @Column(name = "franja_horaria", nullable = false, length = 20)
<<<<<<< HEAD
>>>>>>> mirepo/main
    private String horario;

    // PENDIENTE → ASIGNADA → COMPLETADA (o CANCELADA)
    @Enumerated(EnumType.STRING)
<<<<<<< HEAD
=======
    private String horario;

    @Enumerated(EnumType.STRING)
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
    @Column(nullable = false, length = 20)
    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

    @Column(length = 300)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

<<<<<<< HEAD
    // Enum interno — los estados posibles de una solicitud
    public enum EstadoSolicitud {
        PENDIENTE, ASIGNADA, COMPLETADA, CANCELADA
    }
=======
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

    @Column(name = "descripcion", length = 300)
    private String descripcion;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

>>>>>>> mirepo/main
=======
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
}
