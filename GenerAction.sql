-- 1. Crear la base de datos
CREATE DATABASE IF NOT EXISTS generaction;

-- 2. seleccionar la base de datos para usarla
USE generaction;

-- 3. Tabla de personas mayores 
CREATE TABLE IF NOT EXISTS mayores (
    id_mayor               INT AUTO_INCREMENT PRIMARY KEY,
    nombre                 VARCHAR(80)      NOT NULL,
    apellidos              VARCHAR(120)     NOT NULL,
    telefono               VARCHAR(20)      NOT NULL,
    direccion              VARCHAR(200)     NOT NULL,
    municipio              VARCHAR(100)     NOT NULL,
    fecha_nacimiento       DATE            NOT NULL,
    nivel_autonomia        VARCHAR(10)     NOT NULL,   -- 'alta','media','baja'
    preferencias_actividad VARCHAR(255),
    contacto_familiar_nombre   VARCHAR(120),
    contacto_familiar_telefono VARCHAR(20),
    fecha_alta             TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activo                 BOOLEAN         NOT NULL DEFAULT TRUE
);

-- 4. Tabla de voluntarios
CREATE TABLE IF NOT EXISTS voluntarios (
    id_voluntario       INT AUTO_INCREMENT PRIMARY KEY,
    nombre              VARCHAR(80)      NOT NULL,
    apellidos           VARCHAR(120)     NOT NULL,
    telefono            VARCHAR(20)      NOT NULL,
    email               VARCHAR(120)     NOT NULL UNIQUE,
    password_hash       VARCHAR(255)     NOT NULL,
    universidad         VARCHAR(120),
    estudios            VARCHAR(120),
    barrio              VARCHAR(100),
    estado_verificacion VARCHAR(15)      NOT NULL DEFAULT 'pendiente',
    fecha_alta          TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activo              BOOLEAN          NOT NULL DEFAULT TRUE
);

-- 5. Tabla de solicitudes de ayuda
CREATE TABLE IF NOT EXISTS solicitudes (
    id_solicitud       INT AUTO_INCREMENT PRIMARY KEY,
    id_mayor           INT          NOT NULL,
    tipo_actividad     VARCHAR(30)  NOT NULL,  -- paseo, medico, compra, charla...
    descripcion        VARCHAR(255),
    fecha_solicitada   DATE         NOT NULL,
    franja_horaria     VARCHAR(20)  NOT NULL,  -- mañana / tarde
    estado             VARCHAR(20)  NOT NULL DEFAULT 'pendiente',
    fecha_creacion     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_solicitudes_mayor
        FOREIGN KEY (id_mayor) REFERENCES mayores(id_mayor)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- 6. Tabla de visitas (empareja mayor + voluntario a una solicitud) 
CREATE TABLE IF NOT EXISTS visitas (
    id_visita        INT AUTO_INCREMENT PRIMARY KEY,
    id_solicitud     INT          NOT NULL,
    id_mayor         INT          NOT NULL,
    id_voluntario    INT          NOT NULL,
    fecha_visita     DATETIME     NOT NULL,
    duracion_minutos INT,
    estado           VARCHAR(20)  NOT NULL DEFAULT 'programada', -- programada/realizada/no_realizada

    CONSTRAINT fk_visitas_solicitud
        FOREIGN KEY (id_solicitud)  REFERENCES solicitudes(id_solicitud)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_visitas_mayor
        FOREIGN KEY (id_mayor)      REFERENCES mayores(id_mayor)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_visitas_voluntario
        FOREIGN KEY (id_voluntario) REFERENCES voluntarios(id_voluntario)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- 7. Tabla de valoraciones (satisfacción de mayores y voluntarios)
CREATE TABLE IF NOT EXISTS valoraciones (
    id_valoracion    INT AUTO_INCREMENT PRIMARY KEY,
    id_visita        INT          NOT NULL,
    rol              VARCHAR(10)  NOT NULL,      -- 'mayor' o 'voluntario'
    puntuacion       INT          NOT NULL,      -- 1 a 5
    comentario       VARCHAR(255),
    fecha_valoracion TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_valoraciones_visita
        FOREIGN KEY (id_visita) REFERENCES visitas(id_visita)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

SHOW TABLES;
DESCRIBE voluntarios;

INSERT INTO mayores (
    nombre,
    apellidos,
    telefono,
    direccion,
    municipio,
    fecha_nacimiento,
    nivel_autonomia,
    preferencias_actividad,
    contacto_familiar_nombre,
    contacto_familiar_telefono
) VALUES (
    'Mercedes',
    'García López',
    '600111222',
    'C/ Mayor 10, 3ºB',
    'Madrid',
    '1945-03-12',
    'media',
    'paseos; acompañamiento médico; charla',
    'Ana García',
    '600333444'
);

INSERT INTO voluntarios (
    nombre,
    apellidos,
    telefono,
    email,
    password_hash,
    universidad,
    estudios,
    barrio
) VALUES (
    'Álex',
    'González Pérez',
    '600555666',
    'alex@example.com',
    'hash_demo',      -- aquí luego irá el hash real de la contraseña
    'UFV',
    'Ingeniería Informática',
    'Pozuelo de Alarcón'
);

SELECT * FROM mayores;
SELECT * FROM voluntarios;

INSERT INTO solicitudes (
    id_mayor,
    tipo_actividad,
    descripcion,
    fecha_solicitada,
    franja_horaria
) VALUES (
    1,
    'paseo',
    'Paseo corto por el parque cercano.',
    '2026-02-05',
    'mañana'
);
SELECT * FROM solicitudes;
INSERT INTO visitas (
    id_solicitud,
    id_mayor,
    id_voluntario,
    fecha_visita,
    duracion_minutos,
    estado
) VALUES (
    1,          -- id_solicitud
    1,          -- id_mayor
    1,          -- id_voluntario
    '2026-02-05 10:30:00',
    60,
    'realizada'
);
SELECT * FROM visitas;
-- Valoración del mayor
INSERT INTO valoraciones (
    id_visita,
    rol,
    puntuacion,
    comentario
) VALUES (
    1,
    'mayor',
    5,
    'El joven ha sido muy amable y puntual.'
);

-- Valoración del voluntario
INSERT INTO valoraciones (
    id_visita,
    rol,
    puntuacion,
    comentario
) VALUES (
    1,
    'voluntario',
    4,
    'La visita ha ido muy bien, repetiría.'
);
SELECT * FROM valoraciones;

SELECT *
FROM mayores;

DELETE FROM mayores
WHERE id_mayor IN (2, 3);

SELECT * FROM mayores;
SELECT * FROM solicitudes;
INSERT INTO solicitudes (
    id_mayor,
    tipo_actividad,
    descripcion,
    fecha_solicitada,
    franja_horaria
) VALUES (
    1,                      -- un id_mayor que exista en la tabla mayores
    'paseo',
    'Paseo corto por el parque cercano.',
    '2026-02-05',
    'mañana'
);
SELECT * FROM solicitudes;

INSERT INTO visitas (
    id_solicitud,
    id_mayor,
    id_voluntario,
    fecha_visita,
    duracion_minutos,
    estado
) VALUES (
    3,                     -- aquí pon el id_solicitud que has visto en el SELECT
    1,                     -- id_mayor que exista
    1,                     -- id_voluntario que exista
    '2026-02-05 10:30:00',
    60,
    'realizada'
);
SELECT * FROM valoraciones;
INSERT INTO solicitudes (
    id_mayor,
    tipo_actividad,
    descripcion,
    fecha_solicitada,
    franja_horaria
) VALUES (
    1,                      -- usa un id_mayor que exista en la tabla mayores
    'paseo',
    'Paseo corto por el parque.',
    '2026-02-05',
    'mañana'
);
SELECT * FROM solicitudes;

INSERT INTO visitas (
    id_solicitud,
    id_mayor,
    id_voluntario,
    fecha_visita,
    duracion_minutos,
    estado
) VALUES (
    4,                     -- id_solicitud que aparece en la tabla solicitudes
    1,                     -- id_mayor que existe en mayores
    1,                     -- id_voluntario que existe en voluntarios
    '2026-02-05 10:30:00',
    60,
    'realizada'
);

-- comprueba primero que existen estos IDs
SELECT * FROM mayores;
SELECT * FROM voluntarios;

-- ahora inserta la visita usando un id_solicitud  
INSERT INTO visitas (
    id_solicitud,
    id_mayor,
    id_voluntario,
    fecha_visita,
    duracion_minutos,
    estado
) VALUES (
    1,                     -- id_solicitud que EXISTE
    1,                     -- id_mayor que EXISTE
    1,                     -- id_voluntario que EXISTE
    '2026-02-05 10:30:00',
    60,
    'realizada'
);

-- Valoración del usuario en necesidad 
INSERT INTO valoraciones (
    id_visita,
    rol,
    puntuacion,
    comentario
) VALUES (
    1,                     -- se tiene que cambiar por el id real del usuario
    'mayor',
    5,
    'El voluntario ha sido muy amable y puntual.'
);

-- Valoración del voluntario
INSERT INTO valoraciones (
    id_visita,
    rol,
    puntuacion,
    comentario
) VALUES (
    1,                     -- mismo id_visita
    'voluntario',
    4,
    'La visita ha ido muy bien, repetiría.'
);

SELECT * FROM valoraciones;
-- valoración del mayor
INSERT INTO valoraciones (
    id_visita,
    rol,
    puntuacion,
    comentario
) VALUES (
    1,              -- id_visita que existe en la tabla visitas
    'mayor',        -- indica que es la opinión de la persona mayor
    5,              -- nota de 1 a 5
    'El voluntario ha sido muy amable y puntual.'
);

SELECT * FROM valoraciones;

-- Tabla de notificaciones al familiar del usuario en necesidad 
CREATE TABLE IF NOT EXISTS notificaciones (
    id_notificacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_mayor BIGINT NOT NULL,
    id_visita BIGINT,
    mensaje VARCHAR(500) NOT NULL,
    fecha DATETIME NOT NULL,
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

INSERT INTO notificaciones (
    id_mayor,
    id_visita,
    mensaje,
    fecha,
    leida
) VALUES (
    1,
    1,
    'El voluntario ha llegado a la visita.',
    NOW(),
    FALSE
);

SELECT * FROM notificaciones;