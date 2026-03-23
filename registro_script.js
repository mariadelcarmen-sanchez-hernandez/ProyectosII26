document.getElementById('voluntarioForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    // Recopilar datos
    const formData = new FormData(this);
    const data = Object.fromEntries(formData.entries());
    
    // Manejar los checkboxes de disponibilidad
    const disponibilidades = formData.getAll('disponibilidad');
    data.disponibilidad = disponibilidades;

    console.log("Datos del voluntario:", data);
    alert("¡Gracias por registrarte! Revisaremos tu solicitud pronto.");
    
    // Aquí podrías usar fetch() para enviar a una base de datos
});


