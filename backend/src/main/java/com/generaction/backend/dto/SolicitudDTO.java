package com.generaction.backend.dto;

import lombok.Data;

@Data
public class SolicitudDTO {
    private Long idMayor;
    private String tipoActividad;
    private String fechaSolicitada;
    private String horario;
    private String descripcion;
}