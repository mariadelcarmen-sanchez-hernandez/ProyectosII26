-- =========================================================
-- GenerAction - Base de datos completa
-- Script de estructura para uso con backend y frontend
-- =========================================================

DROP DATABASE IF EXISTS generaction;
CREATE DATABASE generaction;
USE generaction;

-- =========================================================
-- TABLA: mayores
-- =========================================================
CREATE TABLE mayores (
    id_mayor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    apellidos VARCHAR(120) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    municipio VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    nivel_autonomia VARCHAR(10) NOT NULL,
    preferencias_actividad VARCHAR(255),
    contacto_familiar_nombre VARCHAR(120),
    contacto_familiar_telefono VARCHAR(20),
    fecha_alta TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- =========================================================
-- TABLA: voluntarios
-- =========================================================
CREATE TABLE voluntarios (
    id_voluntario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    apellidos VARCHAR(120) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    universidad VARCHAR(120),
    estudios VARCHAR(120),
    barrio VARCHAR(100),
    estado_verificacion VARCHAR(15) NOT NULL DEFAULT 'pendiente',
    fecha_alta TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- =========================================================
-- TABLA: solicitudes
-- =========================================================
CREATE TABLE solicitudes (
    id_solicitud INT AUTO_INCREMENT PRIMARY KEY,
    id_mayor INT NOT NULL,
    tipo_actividad VARCHAR(30) NOT NULL,
    descripcion VARCHAR(255),
    fecha_solicitada DATE NOT NULL,
    franja_horaria VARCHAR(20) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'pendiente',
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_solicitudes_mayor
        FOREIGN KEY (id_mayor) REFERENCES mayores(id_mayor)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- =========================================================
-- TABLA: visitas
-- =========================================================
CREATE TABLE visitas (
    id_visita INT AUTO_INCREMENT PRIMARY KEY,
    id_solicitud INT NOT NULL,
    id_mayor INT NOT NULL,
    id_voluntario INT NOT NULL,
    fecha_visita DATETIME NOT NULL,
    duracion_minutos INT,
    estado VARCHAR(20) NOT NULL DEFAULT 'programada',
    CONSTRAINT fk_visitas_solicitud
        FOREIGN KEY (id_solicitud) REFERENCES solicitudes(id_solicitud)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_visitas_mayor
        FOREIGN KEY (id_mayor) REFERENCES mayores(id_mayor)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_visitas_voluntario
        FOREIGN KEY (id_voluntario) REFERENCES voluntarios(id_voluntario)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- =========================================================
-- TABLA: valoraciones
-- =========================================================
CREATE TABLE valoraciones (
    id_valoracion INT AUTO_INCREMENT PRIMARY KEY,
    id_visita INT NOT NULL,
    rol VARCHAR(10) NOT NULL,
    puntuacion INT NOT NULL,
    comentario VARCHAR(255),
    fecha_valoracion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_valoraciones_visita
        FOREIGN KEY (id_visita) REFERENCES visitas(id_visita)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- =========================================================
-- TABLA: notificaciones
-- =========================================================
CREATE TABLE notificaciones (
    id_notificacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_mayor INT NOT NULL,
    id_visita INT NULL,
    mensaje VARCHAR(500) NOT NULL,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    leida BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_notificacion_mayor
        FOREIGN KEY (id_mayor) REFERENCES mayores(id_mayor)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_notificacion_visita
        FOREIGN KEY (id_visita) REFERENCES visitas(id_visita)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

SELECT * FROM mayores WHERE ID_MAYOR = 1;
SELECT * FROM mayores ORDER BY ID_MAYOR DESC LIMIT 1;

SELECT * FROM mayores;
