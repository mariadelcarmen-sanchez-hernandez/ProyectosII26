const API_BASE = "http://localhost:8080/api";
const voluntarioId = localStorage.getItem("userId");
const token = localStorage.getItem("token");

if (!voluntarioId) {
    alert("No hay sesión iniciada.");
    window.location.href = "login.html";
}

function getAuthHeaders() {
    const headers = {
        "Content-Type": "application/json"
    };

    if (token) {
        headers["Authorization"] = `Bearer ${token}`;
    }

    return headers;
}

function toggleEdit() {
    const view = document.getElementById('profileView');
    const form = document.getElementById('editForm');

    if (view.style.display === 'none') {
        view.style.display = 'block';
        form.style.display = 'none';
    } else {
        view.style.display = 'none';
        form.style.display = 'block';
    }
}

async function cargarPerfilVoluntario() {
    try {
        const response = await fetch(`${API_BASE}/voluntarios/${voluntarioId}`, {
            method: "GET",
            headers: getAuthHeaders()
        });

        if (!response.ok) throw new Error("No se pudo cargar el perfil");

        const voluntario = await response.json();

        document.getElementById("displayName").textContent =
            `${voluntario.nombre || ""} ${voluntario.apellidos || ""}`.trim();

        document.getElementById("displayEmail").textContent =
            voluntario.email || "Sin email";

        document.getElementById("editName").value =
            `${voluntario.nombre || ""} ${voluntario.apellidos || ""}`.trim();

        document.getElementById("editPhone").value =
            voluntario.telefono || "";

        document.getElementById("editEmail").value =
            voluntario.email || "";
    } catch (error) {
        console.error(error);
        document.getElementById("displayName").textContent = "Error al cargar perfil";
        document.getElementById("displayEmail").textContent = "";
    }
}

function formatearFecha(fechaIso) {
    if (!fechaIso) return "Sin fecha";
    return fechaIso.split("T")[0];
}

function crearTarjetaVisita(visita) {
    const mayorNombre = visita.mayor
        ? `${visita.mayor.nombre || ""} ${visita.mayor.apellidos || ""}`.trim()
        : "Mayor no disponible";

    const actividad = visita.solicitud?.tipoActividad || "Actividad no disponible";
    const descripcion = visita.solicitud?.descripcion || "Sin descripción";
    const fecha = formatearFecha(visita.fechaVisita);

    const esProgramada = visita.estado === "PROGRAMADA";

    const icono = esProgramada
        ? `<span class="dot"></span>`
        : `<span class="check">✔</span>`;

    const boton = esProgramada
        ? `<button class="task-action-btn" onclick="marcarRealizada(${visita.idVisita})">Marcar realizada</button>`
        : "";

    return `
        <div class="task-box ${esProgramada ? "pending" : "completed"}">
            ${icono}
            <div class="task-content">
                <p><strong>${actividad}</strong> - ${mayorNombre}</p>
                <p>${descripcion}</p>
                <p><strong>Fecha:</strong> ${fecha}</p>
                <p><strong>Estado:</strong> ${visita.estado}</p>
                ${boton}
            </div>
        </div>
    `;
}

async function cargarVisitasVoluntario() {
    try {
        const response = await fetch(`${API_BASE}/visitas/voluntario/${voluntarioId}`, {
            method: "GET",
            headers: getAuthHeaders()
        });

        if (!response.ok) throw new Error("No se pudieron cargar las visitas");

        const visitas = await response.json();

        const contCurso = document.getElementById("tareasCurso");
        const contFinalizadas = document.getElementById("tareasFinalizadas");

        const enCurso = visitas.filter(v => v.estado === "PROGRAMADA");
        const finalizadas = visitas.filter(v =>
            v.estado === "REALIZADA" || v.estado === "NO_REALIZADA"
        );

        contCurso.innerHTML = enCurso.length
            ? enCurso.map(crearTarjetaVisita).join("")
            : "<p>No tienes tareas en curso.</p>";

        contFinalizadas.innerHTML = finalizadas.length
            ? finalizadas.map(crearTarjetaVisita).join("")
            : "<p>No tienes tareas finalizadas.</p>";
    } catch (error) {
        console.error(error);
        document.getElementById("tareasCurso").innerHTML = "<p>Error al cargar tareas.</p>";
        document.getElementById("tareasFinalizadas").innerHTML = "<p>Error al cargar tareas.</p>";
    }
}

async function marcarRealizada(idVisita) {
    try {
        const response = await fetch(`${API_BASE}/visitas/${idVisita}/estado`, {
            method: "PUT",
            headers: getAuthHeaders(),
            body: JSON.stringify({ estado: "REALIZADA" })
        });

        if (!response.ok) throw new Error("No se pudo actualizar la visita");

        await cargarVisitasVoluntario();
    } catch (error) {
        console.error(error);
        alert("Error al actualizar la visita");
    }
}

document.addEventListener("DOMContentLoaded", async () => {
    await cargarPerfilVoluntario();
    await cargarVisitasVoluntario();
});