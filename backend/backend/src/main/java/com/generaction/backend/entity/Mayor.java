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
    private Boolean activo = true;
}