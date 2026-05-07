const API_BASE = "https://proyectosii26.onrender.com/api";
const voluntarioId = localStorage.getItem("userId");
const nombre = localStorage.getItem("nombre") || "Voluntario";

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

document.addEventListener("DOMContentLoaded", async () => {
    const welcomeText = document.getElementById("welcomeText");
    if (welcomeText) {
        welcomeText.textContent = `¡Gracias por tu tiempo, ${nombre}!`;
    }

    if (!voluntarioId) {
        mostrarEstadoVacio("No hay sesión iniciada.");
        return;
    }

    await Promise.all([
        cargarMisVisitas(),
        cargarSolicitudes()
    ]);
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

function renderVisita(visita) {
    const nombreMayor = visita.mayor
        ? `${visita.mayor.nombre || ""} ${visita.mayor.apellidos || ""}`.trim()
        : "Mayor";

    const tipoActividad = visita.solicitud?.tipoActividad || "Actividad";
    const descripcion = visita.solicitud?.descripcion || "";
    const nombreContactoCodificado = encodeURIComponent(nombreMayor);

    return `
        <div class="volunteer-card visita-card">
            <div class="task-header">
                <span class="category-pill">${tipoActividad}</span>
                <span class="visita-badge">En curso</span>
            </div>
            <h4>${nombreMayor}</h4>
            <p class="task-desc">${descripcion}</p>
            <div class="botones-visita">
                <button class="finalizar-btn" onclick="finalizarVisita(${visita.idVisita})">
                    ✅ Finalizar
                </button>
                <button class="chat-btn-vol" onclick="window.location.href='chat.html?idVisita=${visita.idVisita}&nombreContacto=${nombreContactoCodificado}'">
                    💬 Chatear con ${nombreMayor.split(" ")[0]}
                </button>
            </div>
        </div>
    `;
}

async function cargarMisVisitas() {
    try {
        const res = await fetch(`${API_BASE}/visitas/voluntario/${voluntarioId}`);
        if (!res.ok) return;
        const visitas = await res.json();

        const container = document.getElementById("misVisitas");
        const section = document.getElementById("misVisitasSection");

        if (!Array.isArray(visitas) || visitas.length === 0) {
            section.style.display = "none";
            return;
        }

        const enCurso = visitas.filter(v => v.estado === "PROGRAMADA");
        if (enCurso.length === 0) {
            section.style.display = "none";
            return;
        }

        section.style.display = "block";
        container.innerHTML = enCurso.map(renderVisita).join("");
    } catch (e) {
        console.error("Error cargando visitas del voluntario:", e);
    }
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
            headers: { "Content-Type": "application/json" },
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
        await Promise.all([cargarMisVisitas(), cargarSolicitudes()]);
    } catch (error) {
        console.error("Error aceptando solicitud:", error);
        alert("No se pudo aceptar la solicitud.");
    }
}

async function finalizarVisita(idVisita) {
    if (!confirm("¿Confirmas que has completado esta tarea?")) return;

    const ahora = new Date().toISOString().split(".")[0];

    try {
        const response = await fetch(`${API_BASE}/visitas/${idVisita}/realizar`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                fechaVisita: ahora,
                duracionMinutos: 60
            })
        });

        if (!response.ok) throw new Error("No se pudo finalizar");

        const walletRes = await fetch(`${API_BASE}/voluntarios/${voluntarioId}/wallet`);
        if (walletRes.ok) {
            const wallet = await walletRes.json();
            alert(`✅ ¡Tarea completada! Has ganado 10 puntos. Total: ${wallet.puntosWallet} puntos 🎉`);
        } else {
            alert("✅ ¡Tarea completada! Has ganado 10 puntos 🎉");
        }

        await Promise.all([cargarMisVisitas(), cargarSolicitudes()]);
    } catch (error) {
        console.error("Error finalizando visita:", error);
        alert("No se pudo finalizar la tarea.");
    }
}