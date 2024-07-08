'use strict';
/**
 * Checkout module
 * @returns {{init: init}}
 */
const checkout = () => {
    /**
     * Validate form
     */
    const validateForm = () => {

        const forms = document.querySelectorAll('.needs-validation')
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }

                    form.classList.add('was-validated')
                }, false)
            })
    };

    /**
     * Init
     */
    const init = () => {
        validateForm();
    };

    return {
        init
    };
};

/**
 * Initialize checkout module
 */
document.addEventListener('DOMContentLoaded', () => {
    const checkoutModule = checkout();
    checkoutModule.init();
});