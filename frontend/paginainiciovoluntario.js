const API_BASE = "http://localhost:8080/api";
const nombre = localStorage.getItem("nombre") || "Voluntario";
const token = localStorage.getItem("token");

document.addEventListener("DOMContentLoaded", () => {
    const welcome = document.getElementById("welcomeText");
    if (welcome) {
        welcome.textContent = `¡Gracias por tu tiempo, ${nombre}!`;
    }

    cargarSolicitudes();
});

function getAuthHeaders() {
    const headers = {};
    if (token) {
        headers["Authorization"] = `Bearer ${token}`;
    }
    return headers;
}

function mostrarEstadoVacio(mensaje = "Las tareas disponibles aparecerán aquí cuando se carguen desde el sistema.") {
    const container = document.getElementById("availableTasks");
    container.innerHTML = `
        <div class="empty-state-card">
            <div class="empty-icon">🏠</div>
            <h3>No tienes solicitudes</h3>
            <p>${mensaje}</p>
        </div>
    `;
}

function renderSolicitud(s) {
    return `
        <div class="volunteer-card" data-id="${s.idSolicitud}">
            <div class="task-header">
                <span class="category-pill">${s.tipoActividad || "Ayuda"}</span>
                <span class="task-distance">${s.distancia || ""}</span>
            </div>
            <h4>${s.titulo || "Solicitud"}</h4>
            <p class="task-author">Para: <strong>${s.mayorNombre || "Persona en necesidad"}</strong></p>
            <p class="task-desc">${s.descripcion || ""}</p>
            <button class="accept-btn-vol" onclick="acceptTask(${s.idSolicitud}, this)">Aceptar Solicitud</button>
        </div>
    `;
}

async function cargarSolicitudes() {
    try {
        const res = await fetch(`${API_BASE}/solicitudes/disponibles`, {
            headers: getAuthHeaders()
        });

        if (!res.ok) {
            mostrarEstadoVacio("Todavía no se han podido cargar las solicitudes disponibles.");
            return;
        }

        const solicitudes = await res.json();
        const container = document.getElementById("availableTasks");

        if (!solicitudes || !solicitudes.length) {
            mostrarEstadoVacio();
            return;
        }

        container.innerHTML = solicitudes.map(renderSolicitud).join("");
    } catch (error) {
        console.error("Error cargando solicitudes:", error);
        mostrarEstadoVacio("No se pudo conectar con las solicitudes del servidor.");
    }
}

async function acceptTask(idSolicitud, btn) {
    if (!confirm("¿Deseas comprometerte con esta tarea?")) return;

    try {
        const res = await fetch(`${API_BASE}/solicitudes/${idSolicitud}/aceptar`, {
            method: "PUT",
            headers: {
                ...getAuthHeaders(),
                "Content-Type": "application/json"
            }
        });

        if (!res.ok) {
            throw new Error("No se pudo aceptar la solicitud");
        }

        btn.innerHTML = "✔️ ¡Tarea Asignada!";
        btn.disabled = true;
        await cargarSolicitudes();
    } catch (error) {
        console.error(error);
        alert("No se pudo asignar la solicitud.");
    }
}