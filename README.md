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

## 🧾 Objetivos del proyecto

- Reducir la **soledad no deseada** y aumentar la sensación de acompañamiento de las personas mayores.
- Crear un canal estructurado que permita a los jóvenes **ofrecer ayuda ligera** y compañía en su propio entorno.
- Implementar un sistema de **tareas**: las personas mayores solicitan ayuda, los voluntarios aceptan y realizan esas tareas.
- Diseñar un sistema de **recompensas en puntos** para los voluntarios por cada tarea completada.
- Entregar un **MVP funcional** que cubra las historias de usuario principales y sirva como base para futuras ampliaciones (valoraciones, panel de coordinador, etc.).

---

## 🏗️ Arquitectura técnica

GenerAction sigue una arquitectura cliente–servidor:

- **Backend**: Spring Boot (Java)  
  - Spring Web (API REST)
  - Spring Data JPA (acceso a datos)
  - MySQL (persistencia)
- **Frontend**: React (Vite, JavaScript)  
  - React Router DOM (rutas)
  - Axios (consumo API)
  - Tailwind CSS (estilos)
- **Comunicación**: API REST JSON entre frontend y backend
- **Despliegue objetivo**: empaquetar el build de React dentro del JAR de Spring Boot y/o desplegar en servicios cloud (Render, etc.).

Estructura de carpetas:

```bash
GenerAction-app/
├── backend/      # Proyecto Spring Boot (API + BD)
└── frontend/     # Proyecto React (UI)

👥 Equipo
Carmen Sánchez Hernández

Julián Avilés Medina

Jaime Serres Fernández

Álvaro Fernández-Aller

Walter Andrés Dybek Sánchez

Universidad Francisco de Vitoria – Escuela Politécnica Superior
Grado en Ingeniería Informática – Proyectos II (Convocatoria mayo 2026)
