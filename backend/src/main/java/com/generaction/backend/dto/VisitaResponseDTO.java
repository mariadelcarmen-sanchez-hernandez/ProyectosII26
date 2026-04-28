package com.generaction.backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VisitaResponseDTO {
    private Long idVisita;
    private SolicitudResumenDTO solicitud;
    private MayorResumenDTO mayor;
    private VoluntarioResumenDTO voluntario;
    private LocalDateTime fechaVisita;
    private Integer duracionMinutos;
    private String estado;
}