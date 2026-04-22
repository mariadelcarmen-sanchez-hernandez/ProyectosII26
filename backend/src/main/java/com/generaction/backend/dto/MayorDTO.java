package com.generaction.backend.dto;

import lombok.Data;

@Data
public class MayorDTO {
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private String telefono;
    private String direccion;
    private String municipio;
    private String fechaNacimiento;
    private String nivelAutonomia;
    private String preferenciasActividad;
    private String contactoFamiliarNombre;
    private String contactoFamiliarTelefono;
}