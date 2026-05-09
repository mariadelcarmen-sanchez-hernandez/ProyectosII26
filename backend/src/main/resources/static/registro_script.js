document.getElementById('voluntarioForm').addEventListener('submit', async function(e) {
  e.preventDefault();

  const disponibilidades = Array.from(
    document.querySelectorAll('input[name="disponibilidad"]:checked')
  ).map(cb => cb.value);

  const datos = {
    nombre: document.getElementById('nombre').value,
    apellidos: document.getElementById('apellidos').value,
    email: document.getElementById('email').value,
    telefono: document.getElementById('telefono').value,
    password: document.getElementById('contrasena').value,
    municipio: document.getElementById('ciudad').value,
    direccion: "",
    fechaNacimiento: document.getElementById('fechaNacimiento').value,
    disponibilidad: disponibilidades.join(",")
  };

  try {
    const response = await fetch("https://generaction.onrender.com/api/voluntarios", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(datos)
    });

    if (response.ok) {
      const voluntario = await response.json();

      const loginResponse = await fetch("https://generaction.onrender.com/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email: datos.email, password: datos.password })
      });

      if (loginResponse.ok) {
        const loginData = await loginResponse.json();
        localStorage.setItem("token", loginData.token);
        localStorage.setItem("rol", loginData.rol);
        localStorage.setItem("userId", loginData.id);
        localStorage.setItem("nombre", loginData.nombre);
        alert(`¡Enhorabuena, ${voluntario.nombre}! Tu registro ha sido completado con éxito.`);
        window.location.href = "paginainiciovoluntario.html";
      } else {
        alert(`¡Registro exitoso! Bienvenido/a, ${voluntario.nombre}. Por favor, inicia sesión.`);
        window.location.href = "login.html";
      }
    } else {
      const error = await response.text();
      alert("Error al registrar: " + error);
    }
  } catch (err) {
    alert("No se pudo conectar con el servidor.");
    console.error(err);
  }
});
