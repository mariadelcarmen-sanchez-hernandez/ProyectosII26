# GenerAction – Conectando generaciones, combatiendo la soledad

GenerAction es una plataforma web que aborda el problema de la **soledad no deseada** y la falta de apoyo cotidiano que sufren muchas personas mayores que viven solas en entornos urbanos, especialmente en la Comunidad de Madrid.[file:91] Esta situación afecta a su bienestar emocional, su sensación de seguridad y, en muchos casos, a su salud física y mental.[file:91] Al mismo tiempo existe una brecha intergeneracional: los jóvenes conviven en las mismas ciudades, pero apenas tienen espacios estructurados para relacionarse con personas mayores y ofrecerles compañía y ayuda ligera en su día a día.[file:91]

El proyecto propone un servicio que conecta personas mayores con jóvenes voluntarios de su mismo entorno para realizar acompañamientos como paseos, visitas al médico, compras o conversaciones, coordinado mediante una **plataforma digital sencilla y accesible**, complementada con apoyo telefónico para quienes lo necesiten.[file:91] Cada tarea que un voluntario realiza genera puntos de recompensa, fomentando su participación continuada y creando un impacto social medible.

Este proyecto se desarrolla en la asignatura **Proyectos II** del Grado en Ingeniería Informática de la **Universidad Francisco de Vitoria (UFV)** por el equipo C1. G.A., aplicando metodología ágil (SCRUM) con planificación por sprints, prototipos en Figma y despliegue incremental.[file:91][file:90]

---

## 👥 Equipo

**Equipo C1. G.A. – Convocatoria mayo 2026**:[file:91]

- Carmen Sánchez Hernández  
- Julián Avilés Medina  
- Jaime Serres Fernández  
- Álvaro Fernández-Aller  
- Walter Andrés Dybek Sánchez  

Universidad Francisco de Vitoria – Escuela Politécnica Superior.[file:91]

---

## 🎯 Objetivos del proyecto

### Objetivo general

Mermar lo máximo posible la soledad que padecen las personas mayores y reducir la separación generacional existente entre jóvenes y ancianos, diseñando y desarrollando una solución tecnológica viable que permita organizar de forma segura y sencilla un servicio de acompañamiento entre jóvenes voluntarios y personas mayores que viven solas o en residencias con libertad de movimiento.[file:91]

### Objetivos específicos (resumen)

- Identificar y modelar los **perfiles de usuario principales** (personas mayores, jóvenes voluntarios y coordinadores).[file:91]
- Diseñar y construir una **base de datos** coherente y segura para usuarios, solicitudes de ayuda, visitas y valoraciones.[file:91]
- Desarrollar un **prototipo funcional** (frontend + backend + BD) que permita:
  - Registrar mayores y voluntarios vía web.
  - Crear solicitudes de acompañamiento.
  - Asignar voluntarios a visitas.
  - Registrar la realización de la visita.
  - Valorar las visitas.
  - Generar recompensas en una wallet de puntos.[file:91]
- Definir y documentar **historias de usuario HU1–HU8** como guía del desarrollo iterativo.[file:91]
- Establecer un **plan de trabajo basado en SCRUM** (sprints, revisiones, retrospectivas) con entrega integrada demostrable en mayo.[file:91][file:90]
- Evaluar el impacto del prototipo mediante la **escala UCLA de soledad**.[file:91]

---

## 👤 Perfiles de usuario

El proyecto se apoya en entrevistas y arquetipos de usuario:[file:91]

- **Francisca, 78 años** – Ama de casa, muy culta pero solitaria, interesada en aprender a usar móvil/ordenador para hablar con su nieta.[file:91]
- **María Luisa, 79 años** – Ex enfermera, independiente pero con presión familiar para no salir sola, prefiere llamadas telefónicas.[file:91]
- **Dani, 20 años** – Estudiante de Ingeniería, siente que vive en “burbuja digital”; quiere ayudar, mejorar su CV y le motivan puntos canjeables.[file:91]
- **Lucía, 19 años** – Estudiante de Diseño, muy social; quiere hacer algo útil con su tiempo y valora ver sus horas y personas ayudadas en su perfil.[file:91]

Estos perfiles inspiran las historias de usuario y el diseño de la experiencia (formularios simples, confianza, motivación por recompensas).

---

## 📚 Historias de usuario (HU1–HU8)

Las historias de usuario guían el desarrollo iterativo y están asociadas a sprints concretos:[file:91]

- **HU1 – Alta de persona mayor** (S1, S2, prioridad ALTA)  
  Como persona mayor que vive sola, quiero poder registrarme (con ayuda de un coordinador) para recibir compañía y apoyo.[file:91]

- **HU2 – Alta de voluntario** (S1, S2, prioridad ALTA)  
  Como joven voluntario, quiero crear mi cuenta indicando mis datos y disponibilidad, y ver mi wallet de recompensas.[file:91]

- **HU3 – Creación de solicitud** (S2, prioridad ALTA)  
  Como persona mayor, quiero registrar una solicitud de acompañamiento (actividad, fecha, horario).[file:91]

- **HU4 – Asignación de visita** (S2, S3, prioridad ALTA)  
  Como voluntario, quiero ver solicitudes cercanas y aceptar una.[file:91]

- **HU5 – Registro de visita** (S3, S4, prioridad ALTA)  
  Como voluntario, quiero registrar que he realizado la visita (fecha, duración) para generar métricas y recompensas.[file:91]

- **HU6 – Valoración de visita** (S4, prioridad MEDIA)  
  Como persona mayor, quiero valorar al voluntario con puntuación y comentario.[file:91]

- **HU7 – Panel de coordinador** (S4, prioridad MEDIA)  
  Como responsable del programa, quiero ver un resumen de mayores atendidos, visitas y satisfacción.[file:91]

- **HU8 – Notificaciones a familiares** (S4, S5, prioridad MEDIA)  
  Cuando el voluntario llega, el familiar autorizado recibe un mensaje de confirmación.[file:91]

---

## 🏗️ Tecnologías a implementar

### Frontend

- HTML, CSS, React (Vite, JavaScript).
- React Router para navegación.
- Axios para consumo de API.
- Tailwind CSS para estilos.
- Criterios de accesibilidad inspirados en **WCAG 2.1 AA**, especialmente para personas mayores (fuentes grandes, formularios simplificados).[file:91][file:90]

### Backend

- Java 21.
- Spring Boot (Spring Web, Spring Data JPA, Spring Security).
- API REST para gestionar usuarios, solicitudes/tareas, visitas, valoraciones y recompensas.[file:91][file:90]

### Base de datos

- MySQL (o MariaDB).
- Docker para despliegue y entorno de BD (planeado).[file:91]
- Esquema con tablas para:
  - `usuarios`
  - `solicitudes` / `tareas`
  - `visitas`
  - `valoraciones`
  - `recompensas`[file:91]

### Despliegue (en estudio)

- Docker.
- AWS.
- GitHub Pages (para documentación y/o frontend estático).[file:91]

### Herramientas de desarrollo y comunicación

- VS Code como IDE principal.[file:91][file:90]
- Git y GitHub para control de versiones.[file:91]
- ClickUp como tablero de tareas (backlog, sprints, tareas por miembro).[file:91]

Repositorio GitHub del proyecto:  
`https://github.com/mariadelcarmen-sanchez-hernandez/ProyectosII26.git`[file:91]

---

## 🧱 Arquitectura técnica

Arquitectura cliente–servidor:

- **Frontend (React)**:  
  - Pantallas: registro/login mayores, registro/login voluntarios, creación de solicitud, listado de solicitudes, dashboard de voluntario.
  - Integración progresiva con el backend (primero mocks, luego API real).[file:90][file:91]

- **Backend (Spring Boot)**:  
  - Endpoints REST para HU1–HU8.
  - Persistencia en MySQL (usuarios, solicitudes, visitas, valoraciones, recompensas).[file:91]

- **Integración**:  
  - API JSON sobre HTTP.
  - Desarrollo local: frontend en `localhost:3000`, backend en `localhost:8080`.

---

## 🗓️ Plan de trabajo (Sprints)

### Sprint 1 – Análisis y diseño (2 semanas)

- Refinar problema, objetivos y actores.
- Definir perfiles de usuario principales.
- Priorizar historias de usuario HU1–HU8.
- Diseñar arquitectura del sistema.
- Definir esquema de base de datos.
- Diseñar la plataforma a nivel estético (UI/UX) para mayores, voluntarios y coordinador.
- Seleccionar stack tecnológico.[file:91]

**Entregables Sprint 1**:[file:91]

- Documento de introducción, análisis del problema y objetivos.
- Matriz de competencia con proyectos similares (Adopta un Abuelo, Madrid te Acompaña, Grandes Amigos, etc.).[file:91]
- Historias de usuario HU1–HU8.
- Mockups y prototipo funcional inicial en Figma.
- Repositorio GitHub creado y documentado.[file:91]

### Sprint 2 – Funcionalidades & Frontend (2–3 semanas)

- Configuración inicial del proyecto.
- Desarrollo de componentes UI:
  - Pantalla de registro/login para mayores.
  - Pantalla de registro/login para voluntarios.
  - Pantalla de creación de solicitud (mayor).
  - Pantalla de listado de solicitudes (voluntario).
  - Dashboard básico de voluntario: “mis visitas pendientes”.[file:90][file:91]
- Integración inicial con backend simulado (mocks).
- Testing unitario de componentes.

**Entregables Sprint 2**:[file:90][file:91]

- Repositorio GitHub con la implementación inicial de frontend.
- Flujo completo: Mayor → Solicitud → Voluntario acepta la solicitud (simulado).
- Tests unitarios básicos.
- Documentación de componentes realizados.

### Sprint 3 – Backend y base de datos (2–3 semanas)

- Configuración del proyecto backend.
- Creación de tablas (usuarios, solicitudes, visitas, valoraciones, recompensas).
- Implementación de endpoints para HU1–HU5.
- Testing unitario backend.
- Documentación del desarrollo y unificación con BD.
- Despliegue inicial en local.[file:90][file:91]

### Sprint 4 – Valoraciones y panel básico (2 semanas)

- Implementar sistema de valoración (HU6).
- Persistir ratings 1–5 en backend.
- Componente de valoración en frontend.
- Wallet de recompensas.
- Panel de coordinador (HU7) con dashboard (usuarios activos, visitas, satisfacción media).
- Testing unitario y global.
- Mejoras de UX basadas en feedback.[file:90][file:91]

### Sprint 5 – Estabilización y entrega final (2 semanas)

- Corrección de errores, optimizaciones.
- Testing completo de todos los flujos.
- Validación del despliegue.
- Vídeo demo.
- Preparación de presentación final.
- Redacción de memoria (análisis, resultados, lecciones aprendidas).
- Evaluación de la soledad con escala UCLA.
- Validación global de HU1–HU8.[file:90][file:91]

---

## 🖼️ Mockups y prototipo funcional Figma

Se ha creado un **prototipo funcional interactivo en Figma** que permite validar los requisitos de HU1–HU8 antes de la implementación técnica:[file:91]

- Flujos para Persona en necesidad, Voluntario, Coordinador y Familiar.
- Interfaz responsive para móvil y web.
- Navegación realista que simula el comportamiento final.
- Validación visual de formularios, estados y transiciones.

Enlace al prototipo Figma:[file:91]  
`https://www.figma.com/make/P2Dh38s3F2xm3WSu5jte9N/Aplicaci%C3%B3n-de-conexi%C3%B3n-intergeneracional?p=f&t=vOpC5mp2KJYqIDP7-0&fullscreen=1`

Este prototipo ha permitido detectar mejoras UX, como simplificar formularios para personas mayores (un campo por pantalla, tipografía más grande, ayuda por voz o pop-ups).[file:90][file:91]

---

## 💻 Repositorio GitHub

El código se desarrolla en **Visual Studio Code** con control de versiones Git y organización por ramas de trabajo.[file:91]  
Repositorio principal del proyecto:

```text
https://github.com/mariadelcarmen-sanchez-hernandez/ProyectosII26.git
