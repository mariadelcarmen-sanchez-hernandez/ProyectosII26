package com.generaction.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {

<<<<<<< HEAD
=======
    // Enum interno — los estados posibles de una solicitud
    public enum EstadoSolicitud {
        PENDIENTE,
        ASIGNADA,
        EN_TRAMITE,
        COMPLETADA,
        CANCELADA
    }

>>>>>>> mirepo/main
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitud;

    // Relación con Mayor: una solicitud pertenece a un mayor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mayor", nullable = false)
    private Mayor mayor;

    // Tipo de actividad: paseo, medico, compras, conversacion
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

    @Column(name = "fecha_solicitada", nullable = false)
    private LocalDate fechaSolicitada;

    // "manana" o "tarde"
    @Column(name = "franja_horaria", nullable = false, length = 20)
>>>>>>> mirepo/main
    private String horario;

    // PENDIENTE → ASIGNADA → COMPLETADA (o CANCELADA)
    @Enumerated(EnumType.STRING)
<<<<<<< HEAD
    @Column(nullable = false, length = 20)
    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

    @Column(length = 300)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

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
}
