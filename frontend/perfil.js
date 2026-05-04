const API_BASE = "http://localhost:8080/api";
const userId = localStorage.getItem("userId");
const rol    = localStorage.getItem("rol");
const token  = localStorage.getItem("token");

if (!userId || !rol) {
    alert("No hay sesión iniciada.");
    window.location.href = "login.html";
}

document.addEventListener("DOMContentLoaded", () => {
    const inicioUrl = (rol === "MAYOR")
        ? "paginainiciousuario.html"
        : "paginainiciovoluntario.html";

    const backBtn = document.getElementById("backBtn");
    if (backBtn) backBtn.href = inicioUrl;

    // Footer nav consistente con el resto de la app
    const footer = document.getElementById("footerNav");
    if (footer) {
        footer.innerHTML = `
            <a href="${inicioUrl}" class="nav-item">
                <span class="nav-icon">🏠</span>
                <span>Inicio</span>
            </a>
            <a href="mensajes.html" class="nav-item">
                <span class="nav-icon">💬</span>
                <span>Mensajes</span>
            </a>
            <a href="perfil.html" class="nav-item active">
                <span class="nav-icon">👤</span>
                <span>Perfil</span>
            </a>
        `;
    }

    cargarPerfil();
});

function getAuthHeaders() {
    const headers = { "Content-Type": "application/json" };
    if (token) headers["Authorization"] = `Bearer ${token}`;
    return headers;
}

function toggleEdit() {
    const view = document.getElementById("profileView");
    const form = document.getElementById("editForm");
    const esVista = view.style.display !== "none";
    view.style.display = esVista ? "none" : "block";
    form.style.display = esVista ? "block" : "none";
}

// ─── Carga de perfil según rol ───────────────────────────────────────────────

async function cargarPerfil() {
    if (rol === "MAYOR") {
        await cargarPerfilMayor();
        await cargarSolicitudesMayor();
    } else {
        await cargarPerfilVoluntario();
        await cargarVisitasVoluntario();
    }
}

async function cargarPerfilMayor() {
    try {
        const res = await fetch(`${API_BASE}/mayores/${userId}`, {
            headers: getAuthHeaders()
        });
        if (!res.ok) throw new Error();
        const mayor = await res.json();

        document.getElementById("displayName").textContent =
            `${mayor.nombre || ""} ${mayor.apellidos || ""}`.trim();
        document.getElementById("displayEmail").textContent =
            mayor.email || "Sin email";
        document.getElementById("editName").value =
            `${mayor.nombre || ""} ${mayor.apellidos || ""}`.trim();
        document.getElementById("editPhone").value = mayor.telefono || "";
        document.getElementById("editEmail").value = mayor.email || "";
    } catch {
        document.getElementById("displayName").textContent = "Error al cargar perfil";
    }
}

async function cargarPerfilVoluntario() {
    try {
        const res = await fetch(`${API_BASE}/voluntarios/${userId}`, {
            headers: getAuthHeaders()
        });
        if (!res.ok) throw new Error();
        const voluntario = await res.json();

        document.getElementById("displayName").textContent =
            `${voluntario.nombre || ""} ${voluntario.apellidos || ""}`.trim();
        document.getElementById("displayEmail").textContent =
            voluntario.email || "Sin email";
        document.getElementById("editName").value =
            `${voluntario.nombre || ""} ${voluntario.apellidos || ""}`.trim();
        document.getElementById("editPhone").value = voluntario.telefono || "";
        document.getElementById("editEmail").value = voluntario.email || "";
    } catch {
        document.getElementById("displayName").textContent = "Error al cargar perfil";
    }
}

// ─── Tareas: mayor ve sus solicitudes, voluntario ve sus visitas ──────────────

function formatearFecha(fechaIso) {
    if (!fechaIso) return "Sin fecha";
    return String(fechaIso).split("T")[0];
}

function formatearEstado(estado) {
    const map = {
        PENDIENTE: "Pendiente",
        ASIGNADA: "Asignada",
        EN_TRAMITE: "En trámite",
        COMPLETADA: "Completada",
        CANCELADA: "Cancelada",
        PROGRAMADA: "Programada",
        REALIZADA: "Realizada",
        NO_REALIZADA: "No realizada"
    };
    return map[estado] || estado;
}

async function cargarSolicitudesMayor() {
    const contCurso      = document.getElementById("tareasCurso");
    const contFinalizadas = document.getElementById("tareasFinalizadas");
    try {
        const res = await fetch(`${API_BASE}/solicitudes/mayor/${userId}`, {
            headers: getAuthHeaders()
        });
        if (!res.ok) throw new Error();
        const solicitudes = await res.json();

        const activas    = solicitudes.filter(s => ["PENDIENTE", "ASIGNADA", "EN_TRAMITE"].includes(s.estado));
        const finalizadas = solicitudes.filter(s => ["COMPLETADA", "CANCELADA"].includes(s.estado));

        contCurso.innerHTML = activas.length
            ? activas.map(crearTarjetaSolicitud).join("")
            : "<p>No tienes solicitudes activas.</p>";

        contFinalizadas.innerHTML = finalizadas.length
            ? finalizadas.map(crearTarjetaSolicitud).join("")
            : "<p>No tienes solicitudes finalizadas.</p>";
    } catch {
        contCurso.innerHTML = "<p>Error al cargar solicitudes.</p>";
        contFinalizadas.innerHTML = "";
    }
}

function crearTarjetaSolicitud(solicitud) {
    const activa = ["PENDIENTE", "ASIGNADA", "EN_TRAMITE"].includes(solicitud.estado);
    return `
        <div class="task-box ${activa ? "pending" : "completed"}">
            ${activa ? '<span class="dot"></span>' : '<span class="check">✔</span>'}
            <div class="task-content">
                <p><strong>${solicitud.tipoActividad || "Actividad"}</strong></p>
                <p>${solicitud.descripcion || "Sin descripción"}</p>
                <p><strong>Estado:</strong> ${formatearEstado(solicitud.estado)}</p>
                <p><strong>Fecha:</strong> ${formatearFecha(solicitud.fechaSolicitada)}</p>
            </div>
        </div>
    `;
}

async function cargarVisitasVoluntario() {
    const contCurso       = document.getElementById("tareasCurso");
    const contFinalizadas  = document.getElementById("tareasFinalizadas");
    try {
        const res = await fetch(`${API_BASE}/visitas/voluntario/${userId}`, {
            headers: getAuthHeaders()
        });
        if (!res.ok) throw new Error();
        const visitas = await res.json();

        const enCurso     = visitas.filter(v => v.estado === "PROGRAMADA");
        const finalizadas = visitas.filter(v => ["REALIZADA", "NO_REALIZADA"].includes(v.estado));

        contCurso.innerHTML = enCurso.length
            ? enCurso.map(crearTarjetaVisita).join("")
            : "<p>No tienes tareas en curso.</p>";

        contFinalizadas.innerHTML = finalizadas.length
            ? finalizadas.map(crearTarjetaVisita).join("")
            : "<p>No tienes tareas finalizadas.</p>";
    } catch {
        contCurso.innerHTML = "<p>Error al cargar tareas.</p>";
        contFinalizadas.innerHTML = "";
    }
}

function crearTarjetaVisita(visita) {
    const mayorNombre = visita.mayor
        ? `${visita.mayor.nombre || ""} ${visita.mayor.apellidos || ""}`.trim()
        : "Mayor no disponible";
    const actividad   = visita.solicitud?.tipoActividad || "Actividad";
    const descripcion = visita.solicitud?.descripcion || "Sin descripción";
    const esProgramada = visita.estado === "PROGRAMADA";

    return `
        <div class="task-box ${esProgramada ? "pending" : "completed"}">
            ${esProgramada ? '<span class="dot"></span>' : '<span class="check">✔</span>'}
            <div class="task-content">
                <p><strong>${actividad}</strong> - ${mayorNombre}</p>
                <p>${descripcion}</p>
                <p><strong>Fecha:</strong> ${formatearFecha(visita.fechaVisita)}</p>
                <p><strong>Estado:</strong> ${formatearEstado(visita.estado)}</p>
                ${esProgramada
                    ? `<button class="task-action-btn" onclick="marcarRealizada(${visita.idVisita})">Marcar realizada</button>`
                    : ""}
            </div>
        </div>
    `;
}

async function marcarRealizada(idVisita) {
    try {
        const res = await fetch(`${API_BASE}/visitas/${idVisita}/estado`, {
            method: "PUT",
            headers: getAuthHeaders(),
            body: JSON.stringify({ estado: "REALIZADA" })
        });
        if (!res.ok) throw new Error();
        await cargarVisitasVoluntario();
    } catch {
        alert("Error al actualizar la visita");
    }
}
