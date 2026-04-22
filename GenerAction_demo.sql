-- =========================================================
-- GenerAction - Datos demo
-- Requiere haber ejecutado antes GenerAction.sql
-- =========================================================

USE generaction;

INSERT INTO mayores (
    nombre, apellidos, telefono, direccion, municipio,
    fecha_nacimiento, nivel_autonomia, preferencias_actividad,
    contacto_familiar_nombre, contacto_familiar_telefono
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
    nombre, apellidos, telefono, email, password_hash,
    universidad, estudios, barrio, estado_verificacion, activo
) VALUES (
    'Álex',
    'González Pérez',
    '600555666',
    'alex@example.com',
    'hash_demo',
    'UFV',
    'Ingeniería Informática',
    'Pozuelo de Alarcón',
    'pendiente',
    1
);

INSERT INTO solicitudes (
    id_mayor, tipo_actividad, descripcion, fecha_solicitada, franja_horaria, estado
) VALUES (
    1,
    'paseo',
    'Paseo corto por el parque cercano.',
    '2026-02-05',
    'mañana',
    'pendiente'
);

INSERT INTO visitas (
    id_solicitud, id_mayor, id_voluntario, fecha_visita, duracion_minutos, estado
) VALUES (
    1,
    1,
    1,
    '2026-02-05 10:30:00',
    60,
    'realizada'
);

INSERT INTO valoraciones (
    id_visita, rol, puntuacion, comentario
) VALUES
(
    1,
    'mayor',
    5,
    'El voluntario ha sido muy amable y puntual.'
),
(
    1,
    'voluntario',
    4,
    'La visita ha ido muy bien, repetiría.'
);

INSERT INTO notificaciones (
    id_mayor, id_visita, mensaje, fecha, leida
) VALUES (
    1,
    1,
    'El voluntario ha llegado a la visita.',
    NOW(),
    FALSE
);
