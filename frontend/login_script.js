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
    const resVol = await fetch("http://localhost:8080/api/voluntarios");
    const voluntarios = await resVol.json();

    const voluntario = voluntarios.find(v =>
      v.email && v.email.toLowerCase() === email
    );

    if (voluntario) {
      window.location.href = "paginainiciovoluntario.html";
      return;
    }

    const resMayor = await fetch("http://localhost:8080/api/mayores/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password })
    });

    if (resMayor.ok) {
      window.location.href = "paginainiciousuario.html";
      return;
    }

    const error = await resMayor.text();
    alert(error || "Cuenta no encontrada.");
  } catch (err) {
    alert("No se pudo conectar con el servidor.");
    console.error(err);
  } finally {
    btn.innerText = "Entrar";
    btn.style.opacity = "1";
  }
});