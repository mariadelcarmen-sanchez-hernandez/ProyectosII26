document.getElementById('loginForm').addEventListener('submit', async function(e) {
  e.preventDefault();

  const email = document.getElementById('email').value.trim().toLowerCase();
  const password = document.getElementById('password').value.trim();
  const btn = document.querySelector('.login-btn');

  if (!email || !password) {
    alert('Rellena correo y contraseña.');
    return;
  }

  btn.innerText = "Verificando...";
  btn.style.opacity = "0.7";

  try {
    const res = await fetch("http://localhost:8080/api/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password })
    });

    if (!res.ok) {
      const err = await res.json().catch(() => ({}));
      alert(err.error || "Credenciales incorrectas.");
      return;
    }

    const data = await res.json();
    localStorage.setItem("token", data.token);
    localStorage.setItem("rol", data.rol);
    localStorage.setItem("nombre", data.nombre);
    localStorage.setItem("id", data.id);

    if (data.rol === "ADMIN") {
      window.location.href = "admin.html";
    } else if (data.rol === "VOLUNTARIO") {
      window.location.href = "paginainiciovoluntario.html";
    } else if (data.rol === "MAYOR") {
      window.location.href = "paginainiciousuario.html";
    }
  } catch (err) {
    alert("No se pudo conectar con el servidor.");
    console.error(err);
  } finally {
    btn.innerText = "Entrar";
    btn.style.opacity = "1";
  }
});
