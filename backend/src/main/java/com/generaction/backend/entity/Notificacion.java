package com.generaction.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idMayor;
    private Long idFamiliar;
    private Long idVisita;

    @Column(length = 500)
    private String mensaje;

    private LocalDateTime fecha;

    private Boolean leida = false;

    public Notificacion() {
    }

    public Notificacion(Long idMayor, Long idFamiliar, Long idVisita, String mensaje, LocalDateTime fecha, Boolean leida) {
        this.idMayor = idMayor;
        this.idFamiliar = idFamiliar;
        this.idVisita = idVisita;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.leida = leida;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMayor() {
        return idMayor;
    }

    public void setIdMayor(Long idMayor) {
        this.idMayor = idMayor;
    }

    public Long getIdFamiliar() {
        return idFamiliar;
    }

    public void setIdFamiliar(Long idFamiliar) {
        this.idFamiliar = idFamiliar;
    }

    public Long getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(Long idVisita) {
        this.idVisita = idVisita;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Boolean getLeida() {
        return leida;
    }

    public void setLeida(Boolean leida) {
        this.leida = leida;
    }
}