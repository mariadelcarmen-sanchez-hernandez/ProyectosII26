const API_BASE = "http://localhost:8080/api";
const voluntarioId = localStorage.getItem("userId");
const nombre = localStorage.getItem("nombre") || "Voluntario";

document.addEventListener("DOMContentLoaded", async () => {
    const welcomeText = document.getElementById("welcomeText");
    if (welcomeText) {
        welcomeText.textContent = `¡Gracias por tu tiempo, ${nombre}!`;
    }

    if (!voluntarioId) {
        mostrarEstadoVacio("No hay sesión iniciada.");
        return;
    }

    await cargarSolicitudes();
});

function mostrarEstadoVacio(mensaje = "No hay solicitudes pendientes ahora mismo.") {
    const container = document.getElementById("availableTasks");
    container.innerHTML = `
        <div class="empty-state-card">
            <div class="empty-icon">🏠</div>
            <h3>No tienes solicitudes</h3>
            <p>${mensaje}</p>
        </div>
    `;
}

function renderSolicitud(solicitud) {
    const nombreMayor = solicitud.mayor
        ? `${solicitud.mayor.nombre || ""} ${solicitud.mayor.apellidos || ""}`.trim()
        : "Mayor no disponible";

    return `
        <div class="volunteer-card">
            <div class="task-header">
                <span class="category-pill">${solicitud.tipoActividad || "Actividad"}</span>
                <span class="task-distance">${solicitud.mayor?.municipio || ""}</span>
            </div>

            <h4>${nombreMayor}</h4>
            <p class="task-author">
                <strong>Fecha:</strong> ${solicitud.fechaSolicitada || "Sin fecha"}
            </p>
            <p class="task-author">
                <strong>Horario:</strong> ${solicitud.horario || "Sin horario"}
            </p>
            <p class="task-desc">${solicitud.descripcion || "Sin descripción"}</p>

            <button class="accept-btn-vol" onclick="aceptarSolicitud(${solicitud.idSolicitud})">
                Aceptar solicitud
            </button>
        </div>
    `;
}

async function cargarSolicitudes() {
    try {
        const response = await fetch(`${API_BASE}/solicitudes/disponibles`);

        if (!response.ok) {
            throw new Error("No se pudieron cargar las solicitudes");
        }

        const solicitudes = await response.json();
        const container = document.getElementById("availableTasks");

        if (!Array.isArray(solicitudes) || solicitudes.length === 0) {
            mostrarEstadoVacio();
            return;
        }

        container.innerHTML = solicitudes.map(renderSolicitud).join("");
    } catch (error) {
        console.error("Error cargando solicitudes:", error);
        mostrarEstadoVacio("Error al cargar solicitudes.");
    }
}

async function aceptarSolicitud(idSolicitud) {
    try {
        const response = await fetch(`${API_BASE}/solicitudes/asignar`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                idSolicitud: Number(idSolicitud),
                idVoluntario: Number(voluntarioId)
            })
        });

        if (!response.ok) {
            const errorTexto = await response.text();
            throw new Error(errorTexto || "No se pudo aceptar la solicitud");
        }

        alert("Solicitud aceptada correctamente");
        await cargarSolicitudes();
    } catch (error) {
        console.error("Error aceptando solicitud:", error);
        alert("No se pudo aceptar la solicitud.");
    }
}