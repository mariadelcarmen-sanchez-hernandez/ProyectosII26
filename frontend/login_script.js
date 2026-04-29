document.getElementById("loginForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email, password })
        });

        if (!response.ok) {
            throw new Error("Credenciales incorrectas");
        }

        const data = await response.json();

        localStorage.setItem("token", data.token);
        localStorage.setItem("rol", data.rol);
        localStorage.setItem("userId", data.id);
        localStorage.setItem("nombre", data.nombre);

        if (data.rol === "ADMIN") {
            window.location.href = "admin.html";
        } else if (data.rol === "MAYOR") {
            window.location.href = "paginainiciousuario.html";
        } else if (data.rol === "VOLUNTARIO") {
            window.location.href = "paginainiciovoluntario.html";
        } else {
            alert("Rol no reconocido");
        }
    } catch (error) {
        console.error(error);
        alert("Error al iniciar sesión");
    }
});