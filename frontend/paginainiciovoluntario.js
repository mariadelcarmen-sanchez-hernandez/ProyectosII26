const API_BASE = "http://localhost:8080/api";
const nombre = localStorage.getItem("nombre") || "Voluntario";
const rol = localStorage.getItem("rol");
const token = localStorage.getItem("token");

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("welcomeText").textContent = `¡Gracias por tu tiempo, ${nombre}!`;
    cargarSolicitudes();
});

function getAuthHeaders() {
    const headers = {};
    if (token) headers["Authorization"] = `Bearer ${token}`;
    return headers;
}

async function cargarSolicitudes() {
    try {
        const res = await fetch(`${API_BASE}/solicitudes/disponibles`, {
            headers: getAuthHeaders()
        });

        if (!res.ok) throw new Error("No se pudieron cargar las solicitudes");

        const solicitudes = await res.json();
        const container = document.getElementById("availableTasks");

        if (!solicitudes.length) {
            container.innerHTML = "<p>No hay solicitudes disponibles ahora mismo.</p>";
            return;
        }

        container.innerHTML = solicitudes.map(renderSolicitud).join("");
    } catch (error) {
        console.error(error);
        document.getElementById("availableTasks").innerHTML =
            "<p>Error al cargar solicitudes.</p>";
    }
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

        if (!res.ok) throw new Error("No se pudo aceptar la solicitud");

        btn.innerHTML = "✔️ ¡Tarea Asignada!";
        btn.style.background = "#27ae60";
        btn.disabled = true;

        await cargarSolicitudes();
    } catch (error) {
        console.error(error);
        alert("No se pudo asignar la solicitud.");
    }
}