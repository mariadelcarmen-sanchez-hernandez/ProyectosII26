# GenerAction – Conectando generaciones, combatiendo la soledad

GenerAction es una plataforma web cuyo objetivo es reducir la soledad no deseada y la falta de apoyo cotidiano que sufren muchas personas mayores que viven solas en entornos urbanos. La aplicación conecta a estas personas con jóvenes voluntarios de su mismo entorno para realizar acompañamientos ligeros (paseos, visitas al médico, compras, conversaciones, etc.), coordinados mediante una plataforma digital sencilla y accesible, con apoyo telefónico para quienes lo necesiten.

El proyecto se desarrolla en el contexto de la asignatura **Proyectos II** del Grado en Ingeniería Informática de la **Universidad Francisco de Vitoria (UFV)**, como trabajo del equipo C1. G.A., y aplica metodología ágil por sprints, desde la definición del problema y las historias de usuario hasta el prototipado en Figma y la implementación técnica completa.

---

## 🎯 Problema y contexto

Nuestro foco inicial se centra en las personas mayores de 65 años que viven solas en la Comunidad de Madrid.

Algunas características clave:

- **Magnitud del problema**: En España existen aproximadamente 2,5 millones de personas mayores que viven solas (INE).
- **Necesidad**: La soledad y el apoyo diario son carencias urgentes, con una valoración de 5/5 en necesidad.
- **Accesibilidad**: Son usuarios localizables a través de censos, centros de mayores y servicios sociales (4/5 en accesibilidad).

A la vez, muchos jóvenes viven en las mismas ciudades pero en una “burbuja digital”, con pocas oportunidades estructuradas para relacionarse con personas mayores y ofrecerles compañía real. GenerAction quiere tender ese puente intergeneracional.

---

## 👥 Perfiles de usuario

El diseño del sistema se basa en perfiles reales de usuario recogidos en el análisis:

- **Francisca, 78 años**  
  Fue ama de casa, muy culta pero solitaria. Le gustaría que alguien le ayudase con el móvil u ordenador para poder hablar con su nieta que vive lejos. Le cuesta recurrir a servicios sociales porque tardan mucho y prefiere hablar con una persona real, no con una máquina.

- **María Luisa, 79 años**  
  Fue enfermera, sigue siendo bastante independiente pero su familia insiste en que no salga sola. Pide ayuda por teléfono y a través de la Seguridad Social. Prefiere las llamadas a las apps, y le haría confiar poder conocer un poco al joven que va a ayudarla.

- **Daniel, 20 años**  
  Estudiante de Ingeniería, le gusta la tecnología pero siente que vive aislado de la realidad de su barrio. Quiere conectar con personas mayores y mejorar su CV con experiencias de impacto social. Le motiva saber que ayuda de verdad, y también acumular puntos canjeables (cine, cafeterías, etc.) porque como estudiante va justo de dinero.

A partir de estos perfiles se han definido las **historias de usuario (HU1–HU8)** que guían tanto el diseño del frontend como del backend y la base de datos.

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
