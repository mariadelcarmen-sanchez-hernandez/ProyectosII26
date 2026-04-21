package com.generaction.backend.dto;

import lombok.Data;

@Data
public class VoluntarioDTO {
    private String nombre;
    private String apellidos;
    private String email;
<<<<<<< HEAD
=======
    private String password;
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
    private String telefono;
    private String direccion;
    private String municipio;
    private String fechaNacimiento;
    private String disponibilidad;   // "manana,tarde"
}
