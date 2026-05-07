const API_BASE = "http://localhost:8080/api";
const userId = localStorage.getItem("userId");
const rol    = localStorage.getItem("rol");

if (!userId || !rol) {
    window.location.href = "login.html";
}

const esMayor = rol === "MAYOR";
const inicioUrl = esMayor ? "paginainiciousuario.html" : "paginainiciovoluntario.html";

document.addEventListener("DOMContentLoaded", () => {
    // Botón volver
    document.getElementById("backBtn").href = inicioUrl;

    // Footer nav dinámico según rol
    renderFooter();

    cargarConversaciones();
});

function renderFooter() {
    const footer = document.getElementById("footerNav");
    footer.innerHTML = `
        <a href="${inicioUrl}" class="nav-item">
            <span class="nav-icon">🏠</span>
            <span>Inicio</span>
        </a>
        <a href="mensajes.html" class="nav-item active">
            <span class="nav-icon">💬</span>
            <span>Mensajes</span>
        </a>
        <a href="perfil.html" class="nav-item">
            <span class="nav-icon">👤</span>
            <span>Perfil</span>
        </a>
    `;
}

async function cargarConversaciones() {
    const lista = document.getElementById("conversationsList");
    try {
        const endpoint = esMayor
            ? `${API_BASE}/chat/conversaciones/mayor/${userId}`
            : `${API_BASE}/chat/conversaciones/voluntario/${userId}`;

        const res = await fetch(endpoint);
        if (!res.ok) throw new Error("Error al cargar");

        const conversaciones = await res.json();

        if (!Array.isArray(conversaciones) || conversaciones.length === 0) {
            lista.innerHTML = `
                <div class="empty-msg">
                    <div class="empty-icon">💬</div>
                    <p>Aún no tienes conversaciones.<br>
                       Acepta o recibe una solicitud para poder chatear.</p>
                </div>
            `;
            return;
        }

        lista.innerHTML = conversaciones.map(renderConversacion).join("");
    } catch {
        lista.innerHTML = `<div class="empty-msg">Error al cargar los mensajes.</div>`;
    }
}

function renderConversacion(conv) {
    const inicial = (conv.nombreContacto || "?")[0].toUpperCase();
    const tieneMensaje = !!conv.ultimoMensaje;
    const preview = tieneMensaje
        ? escapeHtml(conv.ultimoMensaje)
        : "Sin mensajes aún — ¡di hola!";
    const hora = conv.fechaUltimoMensaje ? formatearHora(conv.fechaUltimoMensaje) : "";
    const actividad = conv.tipoActividad || "Visita";
    const nombreCodificado = encodeURIComponent(conv.nombreContacto || "Contacto");
    const url = `chat.html?idVisita=${conv.idVisita}&nombreContacto=${nombreCodificado}`;

    return `
        <a class="conv-card ${tieneMensaje ? "" : "sin-mensajes"}" href="${url}">
            <div class="conv-avatar">${inicial}</div>
            <div class="conv-body">
                <div class="conv-header">
                    <span class="conv-nombre">${escapeHtml(conv.nombreContacto || "Contacto")}</span>
                    <span class="conv-hora">${hora}</span>
                </div>
                <div class="conv-actividad">${escapeHtml(actividad)}</div>
                <div class="conv-preview ${tieneMensaje ? "" : "sin-msg"}">${preview}</div>
            </div>
            <span class="conv-arrow">›</span>
        </a>
    `;
}

function formatearHora(fechaIso) {
    const fecha = new Date(fechaIso);
    const hoy = new Date();
    const esHoy = fecha.toDateString() === hoy.toDateString();
    return esHoy
        ? fecha.toLocaleTimeString("es-ES", { hour: "2-digit", minute: "2-digit" })
        : fecha.toLocaleDateString("es-ES", { day: "2-digit", month: "2-digit" });
}

function escapeHtml(texto) {
    const div = document.createElement("div");
    div.appendChild(document.createTextNode(texto));
    return div.innerHTML;
}
