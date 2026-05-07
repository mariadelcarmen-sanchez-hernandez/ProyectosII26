const API_BASE = "https://proyectosii26.onrender.com/api";

const params = new URLSearchParams(window.location.search);
const idVisita = params.get("idVisita");
const nombreContacto = params.get("nombreContacto") || "Contacto";

const miRol = localStorage.getItem("rol");
const miId = Number(localStorage.getItem("userId"));
const miNombre = localStorage.getItem("nombre") || "Yo";

let ultimaFecha = null;
let pollingInterval = null;

document.addEventListener("DOMContentLoaded", () => {
    if (!idVisita) {
        mostrarError("No se especificó una visita.");
        return;
    }

    document.getElementById("contactName").textContent = nombreContacto;

    const input = document.getElementById("messageInput");
    input.addEventListener("keydown", (e) => {
        if (e.key === "Enter" && !e.shiftKey) {
            e.preventDefault();
            enviarMensaje();
        }
    });

    cargarTodosLosMensajes().then(() => {
        pollingInterval = setInterval(cargarMensajesNuevos, 3000);
    });
});

async function cargarTodosLosMensajes() {
    try {
        const res = await fetch(`${API_BASE}/chat/visita/${idVisita}`);
        if (!res.ok) throw new Error("Error al cargar mensajes");
        const mensajes = await res.json();

        const area = document.getElementById("messagesArea");
        area.innerHTML = "";

        if (mensajes.length === 0) {
            area.innerHTML = `
                <div class="chat-empty">
                    <div class="empty-icon">💬</div>
                    <p>Aún no hay mensajes.<br>¡Di hola para empezar!</p>
                </div>
            `;
        } else {
            mensajes.forEach(m => renderMensaje(m, false));
        }

        if (mensajes.length > 0) {
            ultimaFecha = mensajes[mensajes.length - 1].fechaEnvio;
        }

        scrollAbajo();
    } catch (e) {
        mostrarError("No se pudieron cargar los mensajes.");
    }
}

async function cargarMensajesNuevos() {
    if (!ultimaFecha) return;

    try {
        const desde = encodeURIComponent(ultimaFecha);
        const res = await fetch(`${API_BASE}/chat/visita/${idVisita}/nuevos?desde=${desde}`);
        if (!res.ok) return;
        const nuevos = await res.json();

        if (nuevos.length === 0) return;

        const area = document.getElementById("messagesArea");
        const estabaVacio = area.querySelector(".chat-empty");
        if (estabaVacio) area.innerHTML = "";

        nuevos.forEach(m => renderMensaje(m, true));
        ultimaFecha = nuevos[nuevos.length - 1].fechaEnvio;
        scrollAbajo();
    } catch (e) {
        // silencioso para no interrumpir al usuario
    }
}

function renderMensaje(mensaje, animado) {
    const esMio = mensaje.idEmisor === miId && mensaje.rolEmisor === miRol;
    const area = document.getElementById("messagesArea");

    const wrapper = document.createElement("div");
    wrapper.className = `message-wrapper ${esMio ? "mine" : "theirs"}`;
    if (!animado) wrapper.style.animation = "none";

    const hora = formatearHora(mensaje.fechaEnvio);

    wrapper.innerHTML = `
        ${!esMio ? `<div class="message-sender">${escapeHtml(mensaje.nombreEmisor)}</div>` : ""}
        <div class="message-bubble">${escapeHtml(mensaje.contenido)}</div>
        <div class="message-time">${hora}</div>
    `;

    area.appendChild(wrapper);
}

async function enviarMensaje() {
    const input = document.getElementById("messageInput");
    const contenido = input.value.trim();
    if (!contenido) return;

    const btn = document.getElementById("sendBtn");
    btn.disabled = true;
    input.disabled = true;

    try {
        const res = await fetch(`${API_BASE}/chat/enviar`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                idVisita: Number(idVisita),
                rolEmisor: miRol,
                idEmisor: miId,
                nombreEmisor: miNombre,
                contenido: contenido
            })
        });

        if (!res.ok) throw new Error("Error al enviar");

        const mensajeGuardado = await res.json();
        input.value = "";

        const area = document.getElementById("messagesArea");
        const estabaVacio = area.querySelector(".chat-empty");
        if (estabaVacio) area.innerHTML = "";

        renderMensaje(mensajeGuardado, true);
        ultimaFecha = mensajeGuardado.fechaEnvio;
        scrollAbajo();
    } catch (e) {
        alert("No se pudo enviar el mensaje. Inténtalo de nuevo.");
    } finally {
        btn.disabled = false;
        input.disabled = false;
        input.focus();
    }
}

function scrollAbajo() {
    const area = document.getElementById("messagesArea");
    area.scrollTop = area.scrollHeight;
}

function formatearHora(fechaIso) {
    if (!fechaIso) return "";
    const fecha = new Date(fechaIso);
    return fecha.toLocaleTimeString("es-ES", { hour: "2-digit", minute: "2-digit" });
}

function escapeHtml(texto) {
    const div = document.createElement("div");
    div.appendChild(document.createTextNode(texto));
    return div.innerHTML;
}

function mostrarError(msg) {
    document.getElementById("messagesArea").innerHTML = `
        <div class="chat-empty">
            <div class="empty-icon">⚠️</div>
            <p>${msg}</p>
        </div>
    `;
}
