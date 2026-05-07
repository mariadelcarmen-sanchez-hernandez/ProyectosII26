const API_BASE = "https://proyectosii26.onrender.com/api";

let modal;
let taskInput;
let taskContainer;

const mayorId = localStorage.getItem("userId");
const nombre = localStorage.getItem("nombre") || "Usuario";

async function cerrarSesion() {
    const token = localStorage.getItem("token");
    if (token) {
        try {
            await fetch(`${API_BASE}/auth/logout`, {
                method: "POST",
                headers: { "Authorization": `Bearer ${token}` }
            });
        } catch (_) {}
    }
    localStorage.clear();
    window.location.href = "login.html";
}

let tipoActividadSeleccionada = "OTROS";

// Mapa de idSolicitud -> visita (para saber si tiene chat disponible)
let visitasPorSolicitud = {};

document.addEventListener("DOMContentLoaded", async () => {
    modal = document.getElementById("modal");
    taskInput = document.getElementById("taskInput");
    taskContainer = document.getElementById("taskContainer");

    const welcomeText = document.getElementById("welcomeText");
    if (welcomeText) {
        welcomeText.textContent = `Hola, ${nombre}`;
    }

    await Promise.all([
        cargarVisitasMayor(),
        cargarSolicitudesMayor()
    ]);
});

function openModal() {
    modal.style.display = "flex";
}

function closeModal() {
    modal.style.display = "none";
}

function fillForm(tipo, texto) {
    tipoActividadSeleccionada = tipo;

    const input = document.getElementById("taskInput");
    if (input) {
        input.value = texto;
        input.focus();
    }

    document.querySelectorAll(".suggestion-tag").forEach(tag => {
        tag.classList.remove("selected");
    });

    const tags = document.querySelectorAll(".suggestion-tag");
    tags.forEach(tag => {
        if (tag.textContent.includes(texto)) {
            tag.classList.add("selected");
        }
    });
}

window.onclick = function (event) {
    if (event.target === modal) {
        closeModal();
    }
};

function fechaHoy() {
    return new Date().toISOString().split("T")[0];
}

function formatearEstado(estado) {
    switch (estado) {
        case "PENDIENTE":   return "Pendiente";
        case "ASIGNADA":    return "Asignada";
        case "EN_TRAMITE":  return "En trámite";
        case "COMPLETADA":  return "Completada";
        case "CANCELADA":   return "Cancelada";
        default:            return estado;
    }
}

function renderSolicitudes(solicitudes) {
    taskContainer.innerHTML = "";

    if (!solicitudes || solicitudes.length === 0) {
        taskContainer.innerHTML = `
            <div class="empty-state">
                <div class="icon-house">🏠</div>
                <h2>No tienes solicitudes</h2>
                <p>Presiona el botón de arriba para solicitar ayuda</p>
            </div>
        `;
        return;
    }

    const filtradas = solicitudes.filter(s => s.estado !== "COMPLETADA" && s.estado !== "CANCELADA");

    if (filtradas.length === 0) {
        taskContainer.innerHTML = `
            <div class="empty-state">
                <div class="icon-house">🏠</div>
                <h2>No tienes solicitudes activas</h2>
                <p>Presiona el botón de arriba para solicitar ayuda</p>
            </div>
        `;
        return;
    }

    filtradas.forEach((solicitud) => {
        const div = document.createElement("div");
        div.className = "task-item";

        const visita = visitasPorSolicitud[solicitud.idSolicitud];
        const chatBtn = visita
            ? `<button class="btn-chat" onclick="abrirChat(${visita.idVisita}, '${encodeContactName(visita)}')">💬 Chatear</button>`
            : "";

        div.innerHTML = `
            <div class="task-main">
                <span class="task-text">${solicitud.descripcion || "Sin descripción"}</span>
                <div class="task-meta">
                    <span><strong>Tipo:</strong> ${solicitud.tipoActividad || "-"}</span>
                    <span><strong>Estado:</strong> ${formatearEstado(solicitud.estado)}</span>
                </div>
                ${chatBtn}
            </div>
        `;

        taskContainer.appendChild(div);
    });
}

function encodeContactName(visita) {
    const nombre = visita.voluntario
        ? `${visita.voluntario.nombre || ""} ${visita.voluntario.apellidos || ""}`.trim()
        : "Voluntario";
    return encodeURIComponent(nombre);
}

function abrirChat(idVisita, nombreContactoCodificado) {
    window.location.href = `chat.html?idVisita=${idVisita}&nombreContacto=${nombreContactoCodificado}`;
}

async function cargarVisitasMayor() {
    try {
        const res = await fetch(`${API_BASE}/visitas/mayor/${mayorId}/dto`);
        if (!res.ok) return;
        const visitas = await res.json();
        visitasPorSolicitud = {};
        visitas.forEach(v => {
            if (v.solicitud && v.solicitud.idSolicitud) {
                visitasPorSolicitud[v.solicitud.idSolicitud] = v;
            }
        });

        // Detectar visitas REALIZADA sin valorar aún
        const yaValoradas = JSON.parse(localStorage.getItem("visitasValoradas") || "[]");
        const realizadas = visitas.filter(v => v.estado === "REALIZADA" && !yaValoradas.includes(v.idVisita));

        for (const visita of realizadas) {
            yaValoradas.push(visita.idVisita);
            localStorage.setItem("visitasValoradas", JSON.stringify(yaValoradas));
            await mostrarPopupValoracion(visita.idVisita);
        }
    } catch (e) {
        console.error("Error cargando visitas del mayor:", e);
    }
}

async function cargarSolicitudesMayor() {
    try {
        const response = await fetch(`${API_BASE}/solicitudes/mayor/${mayorId}`);

        if (!response.ok) {
            throw new Error("No se pudieron cargar las solicitudes");
        }

        const solicitudes = await response.json();
        renderSolicitudes(solicitudes);
    } catch (error) {
        console.error("Error al cargar solicitudes del mayor:", error);
        taskContainer.innerHTML = `
            <div class="empty-state">
                <div class="icon-house">🏠</div>
                <h2>Error al cargar</h2>
                <p>No se pudieron recuperar tus solicitudes.</p>
            </div>
        `;
    }
}

async function addTask() {
    const descripcion = taskInput.value.trim();

    if (!descripcion) {
        alert("Escribe una solicitud.");
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/solicitudes`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                idMayor: Number(mayorId),
                tipoActividad: tipoActividadSeleccionada,
                descripcion: descripcion,
                fechaSolicitada: fechaHoy(),
                horario: document.getElementById("horarioInput").value
            })
        });

        if (!response.ok) {
            const errorTexto = await response.text();
            throw new Error(errorTexto || "No se pudo guardar la solicitud");
        }

        taskInput.value = "";
        tipoActividadSeleccionada = "OTROS";

        document.querySelectorAll(".suggestion-tag").forEach(tag => {
            tag.classList.remove("selected");
        });

        closeModal();
        await Promise.all([cargarVisitasMayor(), cargarSolicitudesMayor()]);
        alert("Solicitud guardada correctamente");
    } catch (error) {
        console.error("Error al guardar solicitud:", error);
        alert("No se pudo guardar la solicitud.");
    }
}

async function mostrarPopupValoracion(idVisita) {
    const puntuacion = prompt("⭐ ¿Cómo valorarías la ayuda recibida? (1-5 estrellas)");
    if (!puntuacion) return;

    const num = parseInt(puntuacion);
    if (isNaN(num) || num < 1 || num > 5) {
        alert("Por favor introduce un número entre 1 y 5.");
        return;
    }

    const comentario = prompt("💬 ¿Quieres dejar algún comentario? (opcional)") || "";

    try {
        const res = await fetch(`${API_BASE}/valoraciones`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                idVisita: idVisita,
                puntuacion: num,
                comentario: comentario,
                rol: "MAYOR"
            })
        });

        if (res.ok) {
            alert("¡Gracias por tu valoración! 😊");
        } else {
            alert("No se pudo guardar la valoración.");
        }
    } catch (e) {
        console.error("Error enviando valoración:", e);
    }
}