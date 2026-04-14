package com.generaction.backend.dto;

import lombok.Data;

@Data
public class SolicitudDTO {
    private Long idMayor;           // quién pide la ayuda
    private String tipoActividad;   // "paseo", "medico", "compras", "conversacion"
    private String fechaSolicitada; // "2026-05-10"
    private String horario;         // "manana" o "tarde"
    private String descripcion;
}