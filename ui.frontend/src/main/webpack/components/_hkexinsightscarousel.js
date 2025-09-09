// HKEX Insights Carousel Component JavaScript

(function() {
    'use strict';

    class HKEXInsightsCarousel {
        constructor(element) {
            this.element = element;
            this.tilesWrapper = element.querySelector('.content-tiles-wrapper');
            this.tiles = element.querySelectorAll('.content-tile');
            this.prevButton = element.querySelector('.nav-prev');
            this.nextButton = element.querySelector('.nav-next');
            this.currentIndex = 0;
            this.tilesPerView = this.calculateTilesPerView();
            this.maxIndex = Math.max(0, this.tiles.length - this.tilesPerView);
            
            this.init();
        }

        init() {
            if (this.tiles.length === 0) return;
            
            this.setupEventListeners();
            this.updateNavigationState();
            this.setupResizeHandler();
        }

        setupEventListeners() {
            if (this.prevButton) {
                this.prevButton.addEventListener('click', () => this.navigate('prev'));
            }
            
            if (this.nextButton) {
                this.nextButton.addEventListener('click', () => this.navigate('next'));
            }

            // Touch/swipe support for mobile
            this.setupTouchSupport();
        }

        setupTouchSupport() {
            let startX = 0;
            let startY = 0;
            let isDragging = false;

            this.tilesWrapper.addEventListener('touchstart', (e) => {
                startX = e.touches[0].clientX;
                startY = e.touches[0].clientY;
                isDragging = false;
            });

            this.tilesWrapper.addEventListener('touchmove', (e) => {
                if (!startX || !startY) return;
                
                const deltaX = e.touches[0].clientX - startX;
                const deltaY = e.touches[0].clientY - startY;
                
                if (Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > 10) {
                    isDragging = true;
                    e.preventDefault();
                }
            });

            this.tilesWrapper.addEventListener('touchend', (e) => {
                if (!isDragging) return;
                
                const deltaX = e.changedTouches[0].clientX - startX;
                const threshold = 50;
                
                if (Math.abs(deltaX) > threshold) {
                    if (deltaX > 0) {
                        this.navigate('prev');
                    } else {
                        this.navigate('next');
                    }
                }
                
                startX = 0;
                startY = 0;
                isDragging = false;
            });
        }

        navigate(direction) {
            if (direction === 'prev' && this.currentIndex > 0) {
                this.currentIndex--;
            } else if (direction === 'next' && this.currentIndex < this.maxIndex) {
                this.currentIndex++;
            }
            
            this.updateCarouselPosition();
            this.updateNavigationState();
        }

        updateCarouselPosition() {
            if (!this.tilesWrapper) return;
            
            const tileWidth = this.tiles[0]?.offsetWidth || 330;
            const gap = 25; // Gap between tiles
            const translateX = -(this.currentIndex * (tileWidth + gap));
            
            this.tilesWrapper.style.transform = `translateX(${translateX}px)`;
        }

        updateNavigationState() {
            if (this.prevButton) {
                this.prevButton.disabled = this.currentIndex <= 0;
                this.prevButton.classList.toggle('disabled', this.currentIndex <= 0);
            }
            
            if (this.nextButton) {
                this.nextButton.disabled = this.currentIndex >= this.maxIndex;
                this.nextButton.classList.toggle('disabled', this.currentIndex >= this.maxIndex);
            }
        }

        calculateTilesPerView() {
            const containerWidth = this.element.offsetWidth;
            const tileWidth = 330; // Base tile width
            const gap = 25; // Gap between tiles
            const padding = 196; // Left and right padding (98px each)
            
            const availableWidth = containerWidth - padding;
            return Math.floor(availableWidth / (tileWidth + gap));
        }

        setupResizeHandler() {
            let resizeTimeout;
            
            const handleResize = () => {
                clearTimeout(resizeTimeout);
                resizeTimeout = setTimeout(() => {
                    this.tilesPerView = this.calculateTilesPerView();
                    this.maxIndex = Math.max(0, this.tiles.length - this.tilesPerView);
                    
                    // Ensure current index is within bounds
                    if (this.currentIndex > this.maxIndex) {
                        this.currentIndex = this.maxIndex;
                    }
                    
                    this.updateCarouselPosition();
                    this.updateNavigationState();
                }, 250);
            };
            
            window.addEventListener('resize', handleResize);
        }

        // Public method to go to specific slide
        goToSlide(index) {
            if (index >= 0 && index <= this.maxIndex) {
                this.currentIndex = index;
                this.updateCarouselPosition();
                this.updateNavigationState();
            }
        }

        // Public method to refresh carousel
        refresh() {
            this.tilesPerView = this.calculateTilesPerView();
            this.maxIndex = Math.max(0, this.tiles.length - this.tilesPerView);
            this.updateCarouselPosition();
            this.updateNavigationState();
        }
    }

    // Initialize carousels when DOM is ready
    document.addEventListener('DOMContentLoaded', function() {
        const carousels = document.querySelectorAll('.hkex-insights-carousel');
        carousels.forEach(carousel => {
            new HKEXInsightsCarousel(carousel);
        });
    });

    // Export for global access if needed
    if (typeof window !== 'undefined') {
        window.HKEXInsightsCarousel = HKEXInsightsCarousel;
    }

})();
