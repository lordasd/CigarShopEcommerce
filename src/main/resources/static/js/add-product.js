'use strict';

const addProduct = () => {
    const INVALID_NUMBER = "Please enter a valid positive number";
    const INVALID_INTEGER = "Please enter a valid non-negative integer";

    const validateForms = () => {
        const forms = document.querySelectorAll('.needs-validation');
        Array.from(forms).forEach(form => {
            form.addEventListener('submit', event => {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    };

    const validateNumericInputs = () => {
        const priceInput = document.getElementById('price');
        const stockQuantityInput = document.getElementById('stockQuantity');

        if (priceInput) {
            priceInput.addEventListener('input', () => {
                const value = parseFloat(priceInput.value);
                if (isNaN(value) || value < 0) {
                    priceInput.setCustomValidity(INVALID_NUMBER);
                } else {
                    priceInput.setCustomValidity('');
                }
                priceInput.reportValidity();
            });
        }

        if (stockQuantityInput) {
            stockQuantityInput.addEventListener('input', () => {
                const value = parseInt(stockQuantityInput.value);
                if (isNaN(value) || value < 0) {
                    stockQuantityInput.setCustomValidity(INVALID_INTEGER);
                } else {
                    stockQuantityInput.setCustomValidity('');
                }
                stockQuantityInput.reportValidity();
            });
        }
    };

    const init = () => {
        validateForms();
        validateNumericInputs();
    };

    return { init };
};

document.addEventListener('DOMContentLoaded', () => {
    const addProductModule = addProduct();
    addProductModule.init();
});