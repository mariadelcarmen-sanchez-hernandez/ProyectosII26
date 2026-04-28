package com.generaction.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MayorResumenDTO {
    private Long idMayor;
    private String nombre;
    private String apellidos;
    private String municipio;
}