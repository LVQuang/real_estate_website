var togglePassword = document.querySelector('.toggle-password');
var password = document.querySelector('#password');
var eyeIcon = document.querySelector('.toggle-password .bi-eye');
var eyeSlashIcon = document.querySelector('.toggle-password .bi-eye-slash');

togglePassword.addEventListener('click', function () {
    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
    password.setAttribute('type', type);
    eyeIcon.style.display = type === 'password' ? 'block' : 'none';
    eyeSlashIcon.style.display = type === 'password' ? 'none' : 'block';
});