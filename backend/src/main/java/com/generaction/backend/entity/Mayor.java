package com.generaction.backend.entity;

<<<<<<< HEAD
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

=======
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
@Entity
@Table(name = "mayores")
@Data               // Lombok genera getters, setters, toString, equals
@NoArgsConstructor  // Constructor vacío (obligatorio en JPA)
@AllArgsConstructor // Constructor con todos los campos
public class Mayor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
<<<<<<< HEAD
    private Long idMayor;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(nullable = false, length = 120)
    private String apellidos;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(nullable = false, length = 100)
    private String municipio;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false, length = 10)
    private String nivelAutonomia;  // "alto", "medio", "bajo"

    @Column(length = 255)
    private String preferenciasActividad;

    @Column(length = 120)
    private String contactoFamiliarNombre;

    @Column(length = 20)
    private String contactoFamiliarTelefono;

    @Column(nullable = false)
    private LocalDateTime fechaAlta = LocalDateTime.now();

    @Column(nullable = false)
=======
=======
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
    @Column(name = "id_mayor")
    private Long idMayor;

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 120)
    private String apellidos;

    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;

    @Column(name = "municipio", nullable = false, length = 100)
    private String municipio;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "nivel_autonomia", nullable = false, length = 10)
    private String nivelAutonomia;  // "alto", "medio", "bajo"

    @Column(name = "preferencias_actividad", length = 255)
    private String preferenciasActividad;

    @Column(name = "contacto_familiar_nombre", length = 120)
    private String contactoFamiliarNombre;

    @Column(name = "contacto_familiar_telefono", length = 20)
    private String contactoFamiliarTelefono;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDateTime fechaAlta = LocalDateTime.now();

    @Column(name = "activo", nullable = false)
<<<<<<< HEAD
>>>>>>> mirepo/main
    private Boolean activo = true;
=======
    private Boolean activo = true;

    @Column(name = "documento_pdf")
    private byte[] documentoPdf; // Para almacenar el PDF del mayor (HU2)

    @Column(name = "foto_perfil_mayor")
    private byte[] fotoPerfilMayor; // Para almacenar la foto de perfil del mayor (HU2)

    
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
}