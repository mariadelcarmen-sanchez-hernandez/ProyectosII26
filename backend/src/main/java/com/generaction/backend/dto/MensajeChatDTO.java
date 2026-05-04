package com.generaction.backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensajeChatDTO {
    private Long idMensaje;
    private Long idVisita;
    private String rolEmisor;
    private Long idEmisor;
    private String nombreEmisor;
    private String contenido;
    private LocalDateTime fechaEnvio;
}
