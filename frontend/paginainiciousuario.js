const API_BASE = "http://localhost:8080/api";
const modal = document.getElementById("modal");
const taskInput = document.getElementById("taskInput");
const taskContainer = document.getElementById("taskContainer");
const fechaSolicitadaInput = document.getElementById("fechaSolicitada");
const horarioInput = document.getElementById("horario");
const tipoActividadInput = document.getElementById("tipoActividad");

const mayorId = localStorage.getItem("userId");
const nombre = localStorage.getItem("nombre") || "Usuario";

document.addEventListener("DOMContentLoaded", async () => {
    const welcomeText = document.getElementById("welcomeText");
    if (welcomeText) {
        welcomeText.textContent = `Hola, ${nombre}`;
    }

    await cargarSolicitudesMayor();
});

function openModal() {
    modal.style.display = "flex";
}

function closeModal() {
    modal.style.display = "none";
}

function fillForm(tipo, texto) {
    tipoActividadInput.value = tipo;
    taskInput.value = texto;
}

window.onclick = function(event) {
    if (event.target === modal) {
        closeModal();
    }
};

function formatearEstado(estado) {
    switch (estado) {
        case "PENDIENTE":
            return "Pendiente";
        case "ASIGNADA":
            return "Asignada";
        case "EN_TRAMITE":
            return "En trámite";
        case "COMPLETADA":
            return "Completada";
        case "CANCELADA":
            return "Cancelada";
        default:
            return estado;
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

    solicitudes.forEach((solicitud) => {
        const div = document.createElement("div");
        div.className = "task-item";

        div.innerHTML = `
            <div class="task-main">
                <span class="task-text">${solicitud.descripcion || "Sin descripción"}</span>
                <div class="task-meta">
                    <span><strong>Tipo:</strong> ${solicitud.tipoActividad || "-"}</span>
                    <span><strong>Fecha:</strong> ${solicitud.fechaSolicitada || "-"}</span>
                    <span><strong>Horario:</strong> ${solicitud.horario || "-"}</span>
                    <span><strong>Estado:</strong> ${formatearEstado(solicitud.estado)}</span>
                </div>
            </div>
        `;

        taskContainer.appendChild(div);
    });
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
    const fechaSolicitada = fechaSolicitadaInput.value;
    const horario = horarioInput.value;
    const tipoActividad = tipoActividadInput.value;

    if (!descripcion || !fechaSolicitada || !horario || !tipoActividad) {
        alert("Completa todos los campos de la solicitud.");
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
                tipoActividad: tipoActividad,
                descripcion: descripcion,
                fechaSolicitada: fechaSolicitada,
                horario: horario
            })
        });

        if (!response.ok) {
            const errorTexto = await response.text();
            throw new Error(errorTexto || "No se pudo guardar la solicitud");
        }

        taskInput.value = "";
        fechaSolicitadaInput.value = "";
        horarioInput.value = "";
        tipoActividadInput.value = "";

        closeModal();
        await cargarSolicitudesMayor();
        alert("Solicitud guardada correctamente");
    } catch (error) {
        console.error("Error al guardar solicitud:", error);
        alert("No se pudo guardar la solicitud.");
    }
}