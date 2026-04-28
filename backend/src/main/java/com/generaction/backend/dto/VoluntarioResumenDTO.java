package com.generaction.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoluntarioResumenDTO {
    private Long idVoluntario;
    private String nombre;
    private String apellidos;
    private String municipio;
    private Integer puntosWallet;
}