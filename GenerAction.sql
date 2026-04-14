CREATE DATABASE IF NOT EXISTS generaction;
USE generaction;

-- Limpieza previa
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS valoraciones;
DROP TABLE IF EXISTS visitas;
DROP TABLE IF EXISTS solicitudes;
DROP TABLE IF EXISTS voluntarios;
DROP TABLE IF EXISTS mayores;
SET FOREIGN_KEY_CHECKS = 1;

-- Tabla de personas mayores
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
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT chk_mayores_nivel_autonomia
        CHECK (nivel_autonomia IN ('alto', 'medio', 'bajo'))
);

-- Tabla de voluntarios
CREATE TABLE voluntarios (
    id_voluntario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    apellidos VARCHAR(120) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    municipio VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    disponibilidad VARCHAR(100),
    puntos_wallet INT NOT NULL DEFAULT 0,
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabla de solicitudes de ayuda
CREATE TABLE solicitudes (
    id_solicitud INT AUTO_INCREMENT PRIMARY KEY,
    id_mayor INT NOT NULL,
    tipo_actividad VARCHAR(30) NOT NULL,
    descripcion VARCHAR(255),
    fecha_solicitada DATE NOT NULL,
    franja_horaria VARCHAR(20) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_solicitudes_mayor
        FOREIGN KEY (id_mayor) REFERENCES mayores(id_mayor)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT chk_solicitudes_estado
        CHECK (estado IN ('PENDIENTE', 'ASIGNADA', 'EN_TRAMITE', 'COMPLETADA', 'CANCELADA'))
);

-- Tabla de visitas (empareja mayor-voluntario a una solicitud)
CREATE TABLE visitas (
    id_visita INT AUTO_INCREMENT PRIMARY KEY,
    id_solicitud INT NOT NULL,
    id_mayor INT NOT NULL,
    id_voluntario INT NOT NULL,
    fecha_visita DATETIME NULL,
    duracion_minutos INT,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
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
        ON DELETE RESTRICT,
    CONSTRAINT chk_visitas_estado
        CHECK (estado IN ('PENDIENTE', 'ASIGNADA', 'EN_TRAMITE', 'REALIZADA', 'COMPLETADA', 'NO_REALIZADA', 'CANCELADA'))
);

-- Tabla de valoraciones (satisfacción de mayores y voluntarios)
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
        ON DELETE CASCADE,
    CONSTRAINT chk_valoraciones_rol
        CHECK (rol IN ('mayor', 'voluntario')),
    CONSTRAINT chk_valoraciones_puntuacion
        CHECK (puntuacion BETWEEN 1 AND 5)
);

-- Índices para rendimiento
CREATE INDEX idx_mayores_municipio ON mayores(municipio);
CREATE INDEX idx_voluntarios_municipio ON voluntarios(municipio);
CREATE INDEX idx_solicitudes_estado ON solicitudes(estado);
CREATE INDEX idx_solicitudes_mayor ON solicitudes(id_mayor);
CREATE INDEX idx_visitas_estado ON visitas(estado);
CREATE INDEX idx_visitas_voluntario ON visitas(id_voluntario);
CREATE INDEX idx_valoraciones_visita ON valoraciones(id_visita);

-- Datos de prueba mínimos

INSERT INTO mayores (
    nombre, apellidos, telefono, direccion, municipio, fecha_nacimiento,
    nivel_autonomia, preferencias_actividad, contacto_familiar_nombre, contacto_familiar_telefono
) VALUES (
    'Mercedes', 'Garcia Lopez', '600111222', 'C/ Mayor 10, 3B', 'Madrid', '1945-03-12',
    'medio', 'paseos; acompanamiento medico; charla', 'Ana Garcia', '600333444'
);

INSERT INTO voluntarios (
    nombre, apellidos, email, telefono, direccion, municipio, fecha_nacimiento,
    disponibilidad, puntos_wallet
) VALUES (
    'Alex', 'Gonzalez Perez', 'alex@example.com', '600555666', 'C/ Universidad 5', 'Pozuelo de Alarcon', '2000-01-01',
    'manana,tarde', 0
);

INSERT INTO solicitudes (
    id_mayor, tipo_actividad, descripcion, fecha_solicitada, franja_horaria, estado
) VALUES (
    1, 'paseo', 'Paseo corto por el parque cercano.', '2026-04-15', 'manana', 'PENDIENTE'
);

INSERT INTO visitas (
    id_solicitud, id_mayor, id_voluntario, fecha_visita, duracion_minutos, estado
) VALUES (
    1, 1, 1, '2026-04-15 10:30:00', 60, 'COMPLETADA'
);

INSERT INTO valoraciones (
    id_visita, rol, puntuacion, comentario
) VALUES
    (1, 'mayor', 5, 'El voluntario ha sido muy amable y puntual.'),
    (1, 'voluntario', 4, 'La visita ha ido muy bien, repetiria.');

-- Comprobaciones rápidas
SELECT * FROM mayores;
SELECT * FROM voluntarios;
SELECT * FROM solicitudes;
SELECT * FROM visitas;
SELECT * FROM valoraciones;