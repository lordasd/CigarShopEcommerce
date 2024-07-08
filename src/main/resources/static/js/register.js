/**
 * Register module
 * @returns {{init: init}}
 */
const register = () => {
    const DATE_OF_BIRTH = "Please provide your date of birth.";

    /**
     * Initialize the register module
     */
    const validateDateOfBirth = () => {
        const dateOfBirthInput = document.getElementById('dateOfBirth');
        const invalidFeedback = dateOfBirthInput.nextElementSibling;

        dateOfBirthInput.addEventListener('input', () => {
            const dateOfBirthValue = new Date(dateOfBirthInput.value);
            const currentDate = new Date();
            if (dateOfBirthValue.getFullYear() > currentDate.getFullYear()) {
                dateOfBirthInput.setCustomValidity(DATE_OF_BIRTH);
                invalidFeedback.textContent = DATE_OF_BIRTH;
            } else {
                dateOfBirthInput.setCustomValidity('');
                invalidFeedback.textContent = DATE_OF_BIRTH;
            }
        });
    };

    /**
     * Validate the password field
     */
    const validatePassword = () => {
        const passwordInput = document.getElementById('password');
        const passwordFeedback = document.getElementById('password-feedback');

        passwordInput.addEventListener('input', () => {
            const isValid = passwordInput.checkValidity();
            if (!isValid) {
                passwordInput.classList.add('is-invalid');
                passwordFeedback.style.display = 'block';
            } else {
                passwordInput.classList.remove('is-invalid');
                passwordFeedback.style.display = 'none';
            }
        });
    };

    /**
     * Initialize the register module
     */
    const init = () => {
        const forms = document.getElementsByClassName('needs-validation');

        Array.prototype.filter.call(forms, (form) => {
            form.addEventListener('submit', (event) => {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');

                // Show invalid feedback for all invalid fields
                const invalidInputs = form.querySelectorAll(':invalid');
                invalidInputs.forEach(input => {
                    input.classList.add('is-invalid');
                });
            }, false);
        });

        // Password visibility toggle
        const passwordToggle = document.querySelector('.password-toggle');
        if (passwordToggle) {
            passwordToggle.addEventListener('click', () => {
                const passwordInput = document.getElementById('password');
                const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
                passwordInput.setAttribute('type', type);
                passwordToggle.classList.toggle('bi-eye');
                passwordToggle.classList.toggle('bi-eye-slash');
            });
        }

        validateDateOfBirth();
        validatePassword();
    };

    return {
        init
    };
};

// Initialize the register module
document.addEventListener('DOMContentLoaded', () => {
    const registerModule = register();
    registerModule.init();
});