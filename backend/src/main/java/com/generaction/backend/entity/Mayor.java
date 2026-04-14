package com.generaction.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "mayores")
@Data               // Lombok genera getters, setters, toString, equals
@NoArgsConstructor  // Constructor vacío (obligatorio en JPA)
@AllArgsConstructor // Constructor con todos los campos
public class Mayor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
>>>>>>> mirepo/main
    private Boolean activo = true;
}