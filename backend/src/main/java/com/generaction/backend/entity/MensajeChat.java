package com.generaction.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mensajes_chat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensajeChat {

    public enum RolEmisor {
        MAYOR, VOLUNTARIO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    private Long idMensaje;

    @Column(name = "id_visita", nullable = false)
    private Long idVisita;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_emisor", nullable = false, length = 20)
    private RolEmisor rolEmisor;

    @Column(name = "id_emisor", nullable = false)
    private Long idEmisor;

    @Column(name = "nombre_emisor", nullable = false, length = 100)
    private String nombreEmisor;

    @Column(name = "contenido", nullable = false, columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;
}
