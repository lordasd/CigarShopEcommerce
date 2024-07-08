/**
 * Admin module
 * @returns {{init: init}}
 */
const admin = () => {
    /**
     * Show notification modal
     * @param message
     * @param type
     */
    const showNotification = (message, type) => {
        const modalEl = document.getElementById('notificationModal');
        if (!modalEl) return; // Return if modal element is not found

        const modal = new bootstrap.Modal(modalEl);

        const modalBodyEl = document.getElementById('notificationModalBody');
        if (modalBodyEl)
            modalBodyEl.textContent = message;

        const modalTitleEl = document.getElementById('notificationModalLabel');
        modalEl.classList.remove('text-bg-success', 'text-bg-danger');
        if (type === 'success') {
            modalEl.classList.add('text-bg-success');
            if (modalTitleEl)
                modalTitleEl.textContent = 'Success';
        } else if (type === 'error') {
            modalEl.classList.add('text-bg-danger');
            if (modalTitleEl)
                modalTitleEl.textContent = 'Error';
        }

        modal.show();
    };

    /**
     * Initialize the admin module
     */
    const init = () => {
        const notificationDataEl = document.getElementById('notificationData');
        if (notificationDataEl && notificationDataEl.dataset) {
            const notificationMessage = notificationDataEl.dataset.message;
            const notificationType = notificationDataEl.dataset.type;

            if (notificationMessage)
                showNotification(notificationMessage, notificationType);
        }
    };

    return { init };
};

/**
 * Initialize the admin module
 */
document.addEventListener('DOMContentLoaded', () => {
    const adminModule = admin();
    adminModule.init();
});