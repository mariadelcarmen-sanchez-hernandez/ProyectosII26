let currentStep = 1;
const totalSteps = 8;

const prevBtn = document.getElementById('prevBtn');
const nextBtn = document.getElementById('nextBtn');
const progressBar = document.getElementById('progressBar');
const stepText = document.getElementById('stepText');
const steps = document.querySelectorAll('.step');

function construirPayloadMayor() {
  return {
    nombre: document.getElementById('nombre').value.trim(),
    apellidos: document.getElementById('apellidos').value.trim(),
    email: document.getElementById('email').value.trim(),
    password: document.getElementById('contrasena').value.trim(),
    telefono: document.getElementById('telefono').value.trim(),
    direccion: 'No indicada',
    municipio: document.getElementById('ciudad').value.trim() || 'Sin municipio',
    fechaNacimiento: document.getElementById('fechaNacimiento').value || '1950-01-01',
    nivelAutonomia: 'medio',
    preferenciasActividad: document.getElementById('comentarios').value.trim(),
    contactoFamiliarNombre: '',
    contactoFamiliarTelefono: ''
  };
}

async function registrarMayor() {
  const payload = construirPayloadMayor();

  const response = await fetch("https://proyectosii26.onrender.com/api/mayores", {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });

  if (!response.ok) {
    const error = await response.text();
    throw new Error(error || 'No se pudo registrar la persona mayor.');
  }

  return response.json();
}

function validarPasoActual() {
  const currentInputs = steps[currentStep - 1].querySelectorAll('input[required], textarea[required]');
  for (const input of currentInputs) {
    if (!input.value.trim()) {
      alert('Por favor, rellene este campo antes de continuar.');
      return false;
    }
  }
  return true;
}

function updateDisplay() {
  steps.forEach((step, index) => {
    step.classList.toggle('active', index + 1 === currentStep);
  });

  const progress = (currentStep / totalSteps) * 100;
  progressBar.style.width = `${progress}%`;
  stepText.innerText = `Paso ${currentStep} de ${totalSteps}`;
  nextBtn.innerText = currentStep === totalSteps ? 'Finalizar' : 'Siguiente';
}

nextBtn.addEventListener('click', () => {
  if (!validarPasoActual()) return;

  if (currentStep < totalSteps) {
    currentStep++;
    updateDisplay();
  } else {
    const emailMayor = document.getElementById('email').value.trim();
    const passwordMayor = document.getElementById('contrasena').value.trim();

    registrarMayor()
      .then(async (mayor) => {
        const loginResponse = await fetch("https://proyectosii26.onrender.com/api/auth/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ email: emailMayor, password: passwordMayor })
        });

        if (loginResponse.ok) {
          const loginData = await loginResponse.json();
          localStorage.setItem("token", loginData.token);
          localStorage.setItem("rol", loginData.rol);
          localStorage.setItem("userId", loginData.id);
          localStorage.setItem("nombre", loginData.nombre);
          alert(`¡Enhorabuena, ${mayor.nombre}! Tu registro ha sido completado con éxito.`);
          window.location.href = "paginainiciousuario.html";
        } else {
          alert(`Registro completado para ${mayor.nombre}. Por favor, inicia sesión.`);
          window.location.href = "login.html";
        }
      })
      .catch((err) => {
        alert(`Error al registrar: ${err.message}`);
        console.error(err);
      });
  }
});

prevBtn.addEventListener('click', () => {
  if (currentStep > 1) {
    currentStep--;
    updateDisplay();
  } else if (confirm('¿Deseas salir del registro?')) {
    window.history.back();
  }
});

updateDisplay();
