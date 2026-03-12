CREATE DATABASE IF NOT EXISTS generaction;
USE generaction;
SHOW DATABASES;

CREATE TABLE IF NOT EXISTS mayores (
    id_mayor               INT AUTO_INCREMENT PRIMARY KEY,
    nombre                 VARCHAR(80)      NOT NULL,
    apellidos              VARCHAR(120)     NOT NULL,
    telefono               VARCHAR(20)      NOT NULL,
    direccion              VARCHAR(200)     NOT NULL,
    municipio              VARCHAR(100)     NOT NULL,
    fecha_nacimiento       DATE            NOT NULL,
    nivel_autonomia        VARCHAR(10)     NOT NULL,
    preferencias_actividad VARCHAR(255),
    contacto_familiar_nombre   VARCHAR(120),
    contacto_familiar_telefono VARCHAR(20),
    fecha_alta             TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activo                 BOOLEAN         NOT NULL DEFAULT TRUE
);

SHOW TABLES;
DESCRIBE mayores;

