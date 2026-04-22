document.getElementById('loginForm').addEventListener('submit', async function(e) {
  e.preventDefault();

  const email = document.getElementById('email').value.trim().toLowerCase();
  const pass = document.getElementById('password').value.trim();
  const btn = document.querySelector('.login-btn');

  if (!email || !pass) {
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
      window.location.href = "paginainiciovoluntario-5.html";
      return;
    }

    // Si no es voluntario, entra como usuario mayor en modo demo
    window.location.href = "paginainiciousuario-7.html";

  } catch (err) {
    alert("No se pudo conectar con el servidor.");
    console.error(err);
  } finally {
    btn.innerText = "Entrar";
    btn.style.opacity = "1";
  }
});