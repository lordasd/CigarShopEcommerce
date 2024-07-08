'use strict';

/**
 * Reset password module
 * @returns {{init: init}}
 */
const resetPassword = () => {
    const PASSWORDS_DONT_MATCH = "Passwords don't match.";

    /**
     * Validate all forms
     */
    const validateForms = () => {
        const forms = document.querySelectorAll('.needs-validation');
        Array.prototype.slice.call(forms).forEach((form) => {
            form.addEventListener('submit', (event) => {
                if (!form.checkValidity()) {
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
    };

    /**
     * Validate the password match
     */
    const validatePasswordMatch = () => {
        const password = document.getElementById('password');
        const confirmPassword = document.getElementById('confirmPassword');

        if (password && confirmPassword) {
            confirmPassword.addEventListener('input', () => {
                if (password.value !== confirmPassword.value)
                    confirmPassword.setCustomValidity(PASSWORDS_DONT_MATCH);
                else
                    confirmPassword.setCustomValidity('');
            });
        }
    };

    /**
     * Toggle password visibility
     */
    const togglePasswordVisibility = () => {
        const togglePassword = document.getElementById('togglePassword');
        const toggleConfirmPassword = document.getElementById('toggleConfirmPassword');
        const password = document.getElementById('password');
        const confirmPassword = document.getElementById('confirmPassword');

        /**
         * Toggle the visibility of the password field
         * @param inputField
         * @param toggleIcon
         */
        const toggleVisibility = (inputField, toggleIcon) => {
            const type = inputField.getAttribute('type') === 'password' ? 'text' : 'password';
            inputField.setAttribute('type', type);
            toggleIcon.querySelector('i').classList.toggle('bi-eye');
            toggleIcon.querySelector('i').classList.toggle('bi-eye-slash');
        };

        if (togglePassword && password)
            togglePassword.addEventListener('click', () => toggleVisibility(password, togglePassword));

        if (toggleConfirmPassword && confirmPassword)
            toggleConfirmPassword.addEventListener('click', () => toggleVisibility(confirmPassword, toggleConfirmPassword));
    };

    /**
     * Initialize the reset password module
     */
    const init = () => {
        validateForms();
        validatePasswordMatch();
        togglePasswordVisibility();
    };

    return {
        init
    };
};

// Initialize the reset password module
document.addEventListener('DOMContentLoaded', () => {
    const resetPasswordModule = resetPassword();
    resetPasswordModule.init();
});