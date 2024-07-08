'use strict';

/**
 * Forgot password module
 * @returns {{init: init}}
 */
const forgotPassword = () => {
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
                } else
                    showSpinner();
                form.classList.add('was-validated');

                // Show invalid feedback for all invalid fields
                const invalidInputs = form.querySelectorAll(':invalid');
                invalidInputs.forEach(input => { input.classList.add('is-invalid'); });
            }, false);
        });
    };

    /**
     * Validate password match
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
     * Show spinner and hide submit button
     */
    const showSpinner = () => {
        document.getElementById('submitBtn').style.display = 'none';
        document.getElementById('spinner').style.display = 'block';
    };

    /**
     *  Initialize forgot password module
     */
    const init = () => {
        validateForms();
        validatePasswordMatch();
    };

    return {
        init
    };
};

/**
 * Initialize forgot password module
 */
document.addEventListener('DOMContentLoaded', () => {
    const forgotPasswordModule = forgotPassword();
    forgotPasswordModule.init();
});