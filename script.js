// let currentStep = 1;
// const totalSteps = 7;

// const nextBtn = document.getElementById('nextBtn');
// const progressBar = document.getElementById('progressBar');
// const stepText = document.getElementById('stepText');
// const steps = document.querySelectorAll('.step');

// nextBtn.addEventListener('click', () => {
//     // Validar si el input actual está vacío (opcional)
//     const currentInput = steps[currentStep - 1].querySelector('input, textarea');
//     if (currentInput && currentInput.required && !currentInput.value) {
//         alert("Por favor, rellene este campo antes de continuar.");
//         return;
//     }

//     if (currentStep < totalSteps) {
//         // Ocultar paso actual
//         steps[currentStep - 1].classList.remove('active');
        
//         // Siguiente paso
//         currentStep++;
//         steps[currentStep - 1].classList.add('active');

//         // Actualizar visuales
//         updateProgress();
//     } else {
//         alert("¡Registro enviado con éxito!");
//         // Aquí podrías enviar los datos a un servidor
//     }
// });

// function updateProgress() {
//     // Actualizar barra
//     const percent = (currentStep / totalSteps) * 100;
//     progressBar.style.width = percent + "%";

//     // Actualizar texto
//     stepText.innerText = `Paso ${currentStep} de ${totalSteps}`;

//     // Cambiar texto de botón al final
//     if (currentStep === totalSteps) {
//         nextBtn.innerText = "Finalizar";
//     }
// }


let currentStep = 1;
const totalSteps = 7;

const prevBtn = document.getElementById('prevBtn');
const nextBtn = document.getElementById('nextBtn');
const progressBar = document.getElementById('progressBar');
const stepText = document.getElementById('stepText');
const steps = document.querySelectorAll('.step');

function updateDisplay() {
    // Actualizar qué paso se ve
    steps.forEach((s, idx) => {
        s.classList.toggle('active', (idx + 1) === currentStep);
    });

    // Actualizar barra de progreso y texto
    const progress = (currentStep / totalSteps) * 100;
    progressBar.style.width = `${progress}%`;
    stepText.innerText = `Paso ${currentStep} de ${totalSteps}`;

    // Si es el último paso, cambiar texto del botón
    nextBtn.innerText = (currentStep === totalSteps) ? "Finalizar" : "Siguiente →";
}

// LÓGICA BOTÓN SIGUIENTE
nextBtn.addEventListener('click', () => {
    if (currentStep < totalSteps) {
        currentStep++;
        updateDisplay();
    } else {
        alert("¡Formulario Completado!");
    }
});

// LÓGICA BOTÓN VOLVER (Cualquier paso)
prevBtn.addEventListener('click', () => {
    if (currentStep > 1) {
        // Si estamos en cualquier paso mayor a 1, retrocedemos
        currentStep--;
        updateDisplay();
    } else {
        // Si estamos en el paso 1, podemos redirigir a la página de inicio
        if (confirm("¿Deseas salir del registro?")) {
            window.history.back(); 
        }
    }
});

// Inicializar vista
updateDisplay();