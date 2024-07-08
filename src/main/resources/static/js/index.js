'use strict';

/**
 * Index module
 * @returns {{init: init}}
 */
const index = () => {
    const AN_ERROR_OCCURRED = "An error occurred. Please try again.";

    const spinner = document.getElementById('spinner');

    /**
     * Show spinner
     */
    const showSpinner = () => {
        spinner.style.display = 'flex';
    };

    /**
     * Hide spinner
     */
    const hideSpinner = () => {
        spinner.style.display = 'none';
    };

    /**
     * Show toast message
     * @param message
     */
    const showToast = (message) => {
        const toastEl = document.querySelector('.toast');
        const toastBody = toastEl.querySelector('.toast-body');
        toastBody.textContent = message;
        const toast = new bootstrap.Toast(toastEl);
        toast.show();
    };

    /**
     * Update cart count
     * @param count
     */
    const updateCartCount = (count) => {
        const cartCountEl = document.getElementById('cart-count');
        if (cartCountEl) {
            cartCountEl.textContent = count;
        }
    };

    /**
     * Setup htmx event listeners
     */
    const setupHtmxEventListeners = () => {
        document.body.addEventListener('htmx:beforeRequest', showSpinner);
        document.body.addEventListener('htmx:afterRequest', (event) => {
            hideSpinner();
            if (event.detail.elt.closest('form') && event.detail.elt.closest('form').getAttribute('hx-post') === '/cart/add') {
                const response = JSON.parse(event.detail.xhr.response);
                showToast(response.message);
                updateCartCount(response.cartSize);
            }
        });
        document.body.addEventListener('htmx:responseError', () => {
            hideSpinner();
            showToast(AN_ERROR_OCCURRED);
        });
    };

    /**
     * Initialize the index module
     */
    const init = () => {
        setupHtmxEventListeners();
    };

    return {
        init
    };
};

/**
 * Initialize the index module
 */
document.addEventListener('DOMContentLoaded', () => {
    const indexModule = index();
    indexModule.init();
});