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
    fechaNacimiento: "2000-01-01",
    disponibilidad: disponibilidades.join(",")
  };

  try {
    const response = await fetch("http://localhost:8080/api/voluntarios", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(datos)
    });

    if (response.ok) {
      const voluntario = await response.json();
      alert(`¡Registro exitoso! Bienvenido/a, ${voluntario.nombre} 🎉`);
      this.reset();
    } else {
      const error = await response.text();
      alert("Error al registrar: " + error);
    }
  } catch (err) {
    alert("No se pudo conectar con el servidor.");
    console.error(err);
  }
});