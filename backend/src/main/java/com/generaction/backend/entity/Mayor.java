package com.generaction.backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mayores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mayor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mayor")
    private Long idMayor;

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 120)
    private String apellidos;

    @Column(name = "email", nullable = false, length = 120, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String passwordHash;
    
    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;

    @Column(name = "municipio", nullable = false, length = 100)
    private String municipio;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "nivel_autonomia", nullable = false, length = 10)
    private String nivelAutonomia;

    @Column(name = "preferencias_actividad", length = 255)
    private String preferenciasActividad;

    @Column(name = "contacto_familiar_nombre", length = 120)
    private String contactoFamiliarNombre;

    @Column(name = "contacto_familiar_telefono", length = 20)
    private String contactoFamiliarTelefono;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDateTime fechaAlta = LocalDateTime.now();

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Lob
    @Column(name = "documento_pdf")
    private byte[] documentoPdf;

    @Lob
    @Column(name = "foto_perfil_mayor")
    private byte[] fotoPerfilMayor;
}