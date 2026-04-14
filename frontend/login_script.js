document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const email = document.getElementById('email').value;
    const pass = document.getElementById('password').value;

    console.log("Intentando entrar con:", email);

    // Animación de botón al hacer clic
    const btn = document.querySelector('.login-btn');
    btn.innerText = "Verificando...";
    btn.style.opacity = "0.7";

    setTimeout(() => {
        alert("¡Acceso correcto! Bienvenido.");
        btn.innerText = "Entrar";
        btn.style.opacity = "1";
    }, 1500);
});