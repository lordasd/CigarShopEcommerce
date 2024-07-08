'use strict';

/**
 * Login module
 * @returns {{init: init}}
 */
const login = () => {
    /**
     * Validate form
     */
    const validateForm = () => {
        const formss = document.querySelectorAll('.needs-validation');
        Array.prototype.slice.call(formss).forEach((form) => {
            form.addEventListener('submit', (event) => {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');

                const invalidInputs = form.querySelectorAll(':invalid');
                invalidInputs.forEach(input => { input.classList.add('is-invalid'); });
            }, false);
        });
    };

    /**
     * Initialize the module
     */
    const init = () => {
        validateForm();
    };

    return {
        init
    };
};

/**
 * Initialize the login module
 */
document.addEventListener('DOMContentLoaded', () => {
    const loginModule = login();
    loginModule.init();
});