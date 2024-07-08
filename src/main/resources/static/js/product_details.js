"use strict";

/**
 * Module for interactive star rating system on product details page.
 * @returns {{init: init}}
 */
const productDetails = () => {
    const STAR_RATING = "Please select a star rating.";

    /**
     * Update star rating display based on user input.
     * @param starContainer
     * @param ratingInput
     */
    const updateStars = (starContainer, ratingInput) => {
        if (!starContainer || !ratingInput) return;

        const stars = starContainer.querySelectorAll('.bi');

        const updateStarDisplay = (value) => {
            stars.forEach((star, index) => {
                if (index < value) {
                    star.classList.remove('bi-star', 'text-muted');
                    star.classList.add('bi-star-fill', 'text-warning');
                } else {
                    star.classList.remove('bi-star-fill', 'text-warning');
                    star.classList.add('bi-star', 'text-muted');
                }
            });
        };

        // Set initial state
        updateStarDisplay(parseInt(ratingInput.value) || 0);

        starContainer.addEventListener('click', function(event) {
            if (event.target.classList.contains('bi')) {
                const value = parseInt(event.target.getAttribute('data-value'));
                ratingInput.value = value;
                updateStarDisplay(value);
                ratingInput.setCustomValidity('');
                ratingInput.classList.remove('is-invalid');
            }
        });

        // Add hover effect
        stars.forEach((star) => {
            star.addEventListener('mouseenter', function() {
                const value = parseInt(this.getAttribute('data-value'));
                updateStarDisplay(value);
            });

            star.addEventListener('mouseleave', function() {
                updateStarDisplay(parseInt(ratingInput.value) || 0);
            });
        });
    };

    /**
     * Validate form on submission.
     * @param form
     * @param ratingInput
     */
    const validateForm = (form, ratingInput) => {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }

            if (parseInt(ratingInput.value) === 0) {
                ratingInput.setCustomValidity(STAR_RATING);
                ratingInput.classList.add('is-invalid');
                event.preventDefault();
                event.stopPropagation();
            } else
                ratingInput.setCustomValidity('');

            form.classList.add('was-validated');
        }, false);
    };

    /**
     * Initialize interactive star rating system.
     */
    const init = () => {
        // Initialize interactive stars for new review
        const newRatingStars = document.getElementById('new-rating-stars');
        const newRatingInput = document.getElementById('new-rating');
        const newReviewForm = document.getElementById('newReviewForm');
        if (newRatingStars && newRatingInput && newReviewForm) {
            updateStars(newRatingStars, newRatingInput);
            validateForm(newReviewForm, newRatingInput);
        }

        // Initialize interactive stars for each existing review in edit mode
        document.addEventListener('click', function(event) {
            if (event.target.classList.contains('btn-secondary') && event.target.getAttribute('data-toggle') === 'collapse') {
                const reviewId = event.target.getAttribute('data-target').replace('#editReview-', '');
                const starContainer = document.getElementById(`edit-rating-stars-${reviewId}`);
                const ratingInput = document.getElementById(`rating-${reviewId}`);
                const editForm = document.getElementById(`editReviewForm-${reviewId}`);
                if (starContainer && ratingInput && editForm) {
                    updateStars(starContainer, ratingInput);
                    validateForm(editForm, ratingInput);
                }
            }
        });
    };

    return {
        init
    };
};

/**
 * Initialize product details module.
 */
document.addEventListener('DOMContentLoaded', () => {
    const productDetailsModule = productDetails();
    productDetailsModule.init();
});