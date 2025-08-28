/**
 * HKEX Insights Carousel Component JavaScript
 * Provides carousel navigation and interactive functionality
 */

(function() {
    'use strict';

    class HkexInsightsCarousel {
        constructor(element) {
            this.element = element;
            this.articlesContainer = element.querySelector('.articles-container');
            this.navLeft = element.querySelector('.nav-btn-left');
            this.navRight = element.querySelector('.nav-btn-right');
            this.articleCards = element.querySelectorAll('.article-card');
            
            this.currentIndex = 0;
            this.visibleCards = this.calculateVisibleCards();
            this.maxIndex = Math.max(0, this.articleCards.length - this.visibleCards);
            
            this.init();
        }

        init() {
            this.bindEvents();
            this.updateNavigationState();
            this.setupTouchSupport();
        }

        bindEvents() {
            if (this.navLeft) {
                this.navLeft.addEventListener('click', () => this.navigateLeft());
            }
            
            if (this.navRight) {
                this.navRight.addEventListener('click', () => this.navigateRight());
            }

            // Keyboard navigation
            document.addEventListener('keydown', (e) => {
                if (this.element.contains(document.activeElement)) {
                    if (e.key === 'ArrowLeft') {
                        e.preventDefault();
                        this.navigateLeft();
                    } else if (e.key === 'ArrowRight') {
                        e.preventDefault();
                        this.navigateRight();
                    }
                }
            });

            // Window resize handling
            window.addEventListener('resize', () => {
                this.visibleCards = this.calculateVisibleCards();
                this.maxIndex = Math.max(0, this.articleCards.length - this.visibleCards);
                this.updateNavigationState();
            });
        }

        calculateVisibleCards() {
            const containerWidth = this.articlesContainer.offsetWidth;
            const cardWidth = 330; // Base card width
            const gap = 25; // Gap between cards
            
            // Calculate how many cards can fit in the visible area
            const visibleCards = Math.floor((containerWidth + gap) / (cardWidth + gap));
            return Math.max(1, visibleCards);
        }

        navigateLeft() {
            if (this.currentIndex > 0) {
                this.currentIndex--;
                this.scrollToIndex();
            }
        }

        navigateRight() {
            if (this.currentIndex < this.maxIndex) {
                this.currentIndex++;
                this.scrollToIndex();
            }
        }

        scrollToIndex() {
            const cardWidth = 330; // Base card width
            const gap = 25; // Gap between cards
            const scrollPosition = this.currentIndex * (cardWidth + gap);
            
            this.articlesContainer.scrollTo({
                left: scrollPosition,
                behavior: 'smooth'
            });
            
            this.updateNavigationState();
        }

        updateNavigationState() {
            // Update left navigation button state
            if (this.navLeft) {
                this.navLeft.disabled = this.currentIndex <= 0;
                this.navLeft.classList.toggle('disabled', this.currentIndex <= 0);
            }
            
            // Update right navigation button state
            if (this.navRight) {
                this.navRight.disabled = this.currentIndex >= this.maxIndex;
                this.navRight.classList.toggle('disabled', this.currentIndex >= this.maxIndex);
            }
        }

        setupTouchSupport() {
            let startX = 0;
            let startY = 0;
            let isScrolling = false;

            this.articlesContainer.addEventListener('touchstart', (e) => {
                startX = e.touches[0].clientX;
                startY = e.touches[0].clientY;
                isScrolling = false;
            }, { passive: true });

            this.articlesContainer.addEventListener('touchmove', (e) => {
                if (!isScrolling) {
                    const deltaX = Math.abs(e.touches[0].clientX - startX);
                    const deltaY = Math.abs(e.touches[0].clientY - startY);
                    
                    if (deltaX > deltaY) {
                        isScrolling = true;
                    }
                }
            }, { passive: true });

            this.articlesContainer.addEventListener('touchend', (e) => {
                if (!isScrolling) {
                    const endX = e.changedTouches[0].clientX;
                    const deltaX = startX - endX;
                    const threshold = 50; // Minimum swipe distance

                    if (Math.abs(deltaX) > threshold) {
                        if (deltaX > 0) {
                            this.navigateRight();
                        } else {
                            this.navigateLeft();
                        }
                    }
                }
            }, { passive: true });
        }

        // Public method to go to specific index
        goToIndex(index) {
            if (index >= 0 && index <= this.maxIndex) {
                this.currentIndex = index;
                this.scrollToIndex();
            }
        }

        // Public method to refresh carousel
        refresh() {
            this.visibleCards = this.calculateVisibleCards();
            this.maxIndex = Math.max(0, this.articleCards.length - this.visibleCards);
            this.updateNavigationState();
        }
    }

    // Initialize carousels when DOM is ready
    function initCarousels() {
        const carousels = document.querySelectorAll('.hkex-insights-carousel');
        carousels.forEach(carousel => {
            new HkexInsightsCarousel(carousel);
        });
    }

    // Initialize on DOM ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initCarousels);
    } else {
        initCarousels();
    }

    // Export for global access if needed
    if (typeof window !== 'undefined') {
        window.HkexInsightsCarousel = HkexInsightsCarousel;
    }

})();
