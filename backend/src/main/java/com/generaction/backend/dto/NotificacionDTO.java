package com.generaction.backend.dto;

import java.time.LocalDateTime;

public class NotificacionDTO {

    private Long id;
    private Long idMayor;
    private Long idFamiliar;
    private Long idVisita;
    private String mensaje;
    private LocalDateTime fecha;
    private Boolean leida;

    public NotificacionDTO() {
    }

    public NotificacionDTO(Long id, Long idMayor, Long idFamiliar, Long idVisita, String mensaje, LocalDateTime fecha, Boolean leida) {
        this.id = id;
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