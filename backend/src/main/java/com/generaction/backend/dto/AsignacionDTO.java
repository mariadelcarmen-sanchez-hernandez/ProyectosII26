package com.generaction.backend.dto;

import lombok.Data;

// Lo que envía el voluntario cuando acepta una solicitud (HU4)
@Data
public class AsignacionDTO {
    private Long idSolicitud;
    private Long idVoluntario;
}