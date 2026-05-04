package com.generaction.backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversacionResumenDTO {
    private Long idVisita;
    private String nombreContacto;
    private String tipoActividad;
    private String ultimoMensaje;
    private LocalDateTime fechaUltimoMensaje;
}
