package com.generaction.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolicitudResumenDTO {
    private Long idSolicitud;
    private String tipoActividad;
    private String descripcion;
    private LocalDate fechaSolicitada;
    private String horario;
    private String estado;
    private LocalDateTime fechaCreacion;
}