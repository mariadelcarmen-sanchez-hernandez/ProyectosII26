document.getElementById('voluntarioForm').addEventListener('submit', async function(e) {
    e.preventDefault();

    // Recoger disponibilidad (checkboxes múltiples)
    const disponibilidades = Array.from(
        document.querySelectorAll('input[name="disponibilidad"]:checked')
    ).map(cb => cb.value);

    const datos = {
        nombre:        document.getElementById('nombre').value,
        apellidos:     document.getElementById('apellidos').value,
        email:         document.getElementById('email').value,
        telefono:      document.getElementById('telefono').value,
        municipio:     document.getElementById('ciudad').value,
        direccion:     "",                          // añade campo si lo tenéis
        fechaNacimiento: "2000-01-01",              // añade campo fecha si lo tenéis
        disponibilidad: disponibilidades.join(",")  // "manana,tarde"
    };

    try {
        const response = await fetch("/api/voluntarios", {
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
        alert("No se pudo conectar con el servidor. ¿Está arrancado Spring Boot?");
        console.error(err);
    }
});

