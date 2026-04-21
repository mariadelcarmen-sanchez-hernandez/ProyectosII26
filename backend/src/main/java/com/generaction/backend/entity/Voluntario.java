package com.generaction.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "voluntarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voluntario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
    private Long idVoluntario;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(nullable = false, length = 120)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

<<<<<<< HEAD
=======
    @Column(nullable = false, length = 255)
    private String password;

>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(nullable = false, length = 100)
    private String municipio;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

<<<<<<< HEAD
    // Disponibilidad horaria (ej: "mañana,tarde")
    @Column(length = 100)
    private String disponibilidad;

    // Wallet de puntos (HU2)
    @Column(nullable = false)
    private Integer puntosWallet = 0;

=======
    @Column(length = 100)
    private String disponibilidad;

    @Column(nullable = false)
    private Integer puntosWallet = 0;

    @Lob
    private byte[] documentoPdf;

>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
    @Column(nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Column(nullable = false)
<<<<<<< HEAD
=======
    @Column(name = "id_voluntario")
    private Long idVoluntario;

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 120)
    private String apellidos;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;

    @Column(name = "municipio", nullable = false, length = 100)
    private String municipio;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    // Disponibilidad horaria (ej: "mañana,tarde")
    @Column(name = "disponibilidad", length = 100)
    private String disponibilidad;

    // Wallet de puntos (HU2)
    @Column(name = "puntos_wallet", nullable = false)
    private Integer puntosWallet = 0;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Column(name = "activo", nullable = false)
>>>>>>> mirepo/main
    private Boolean activo = true;
}
=======
    private Boolean activo = true;
}
>>>>>>> 68d22c3b88f54e86832ea9064627a9ed650ba773
