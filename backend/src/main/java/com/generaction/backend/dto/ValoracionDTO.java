package com.generaction.backend.dto;

import lombok.Data;

@Data
public class ValoracionDTO {
    private Long idVisita;
    private String rol;       // "mayor" o "voluntario"
    private Integer puntuacion;
    private String comentario;
}