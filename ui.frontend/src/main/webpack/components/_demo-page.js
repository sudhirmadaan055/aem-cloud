/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Demo Page Component JavaScript
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

(function() {
    'use strict';

    /**
     * Demo Page Component
     * Handles demo page specific functionality
     */
    class DemoPage {
        constructor() {
            this.init();
        }

        init() {
            this.bindEvents();
            this.initializeComponents();
        }

        bindEvents() {
            // Add any demo page specific event listeners here
            document.addEventListener('DOMContentLoaded', () => {
                console.log('Demo Page component initialized');
            });
        }

        initializeComponents() {
            // Initialize any demo page specific components
            this.initializeImageLazyLoading();
        }

        initializeImageLazyLoading() {
            // Add lazy loading functionality for images if needed
            const images = document.querySelectorAll('.demo-page img[data-src]');
            
            if ('IntersectionObserver' in window) {
                const imageObserver = new IntersectionObserver((entries, observer) => {
                    entries.forEach(entry => {
                        if (entry.isIntersecting) {
                            const img = entry.target;
                            img.src = img.dataset.src;
                            img.classList.remove('lazy');
                            imageObserver.unobserve(img);
                        }
                    });
                });

                images.forEach(img => imageObserver.observe(img));
            } else {
                // Fallback for browsers without IntersectionObserver
                images.forEach(img => {
                    img.src = img.dataset.src;
                    img.classList.remove('lazy');
                });
            }
        }
    }

    // Initialize the component
    new DemoPage();
})();
