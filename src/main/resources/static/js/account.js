'use strict';

const account = () => {
    const initFormValidation = () => {
        window.addEventListener('load', function () {
            const forms = document.getElementsByClassName('needs-validation');
            Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');

                    // Custom password validation
                    const newPassword = document.getElementById('newPassword');
                    const confirmPassword = document.getElementById('confirmPassword');
                    if (newPassword && confirmPassword) {
                        const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/;
                        if (!passwordRegex.test(newPassword.value)) {
                            newPassword.setCustomValidity("Password must contain at least one number, one uppercase and lowercase letter, and at least 8 or more characters.");
                        } else {
                            newPassword.setCustomValidity("");
                        }
                        if (newPassword.value !== confirmPassword.value) {
                            confirmPassword.setCustomValidity("Passwords do not match");
                        } else {
                            confirmPassword.setCustomValidity("");
                        }
                    }
                }, false);
            });
        }, false);
    };

    return {
        initFormValidation
    };
};

document.addEventListener("DOMContentLoaded", () => {
    const accountModule = account();
    accountModule.initFormValidation();
});