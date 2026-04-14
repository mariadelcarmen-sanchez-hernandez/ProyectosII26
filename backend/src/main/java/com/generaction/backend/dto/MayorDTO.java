package com.generaction.backend.dto;

import lombok.Data;

@Data
public class MayorDTO {
    private String nombre;
    private String apellidos;
    private String telefono;
    private String direccion;
    private String municipio;
    private String fechaNacimiento;   // "1946-03-15"
    private String nivelAutonomia;    // "alto", "medio", "bajo"
    private String preferenciasActividad;
    private String contactoFamiliarNombre;
    private String contactoFamiliarTelefono;
}
