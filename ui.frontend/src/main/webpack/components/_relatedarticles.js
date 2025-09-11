/**
 * Related Articles Carousel Component
 * Handles carousel navigation, pagination, and responsive behavior
 */

(function() {
    'use strict';

    // Initialize carousel when DOM is ready
    document.addEventListener('DOMContentLoaded', function() {
        const carousels = document.querySelectorAll('.related-articles');
        
        carousels.forEach(function(carousel) {
            initCarousel(carousel);
        });
    });

    function initCarousel(carousel) {
        const carouselContainer = carousel.querySelector('.related-articles__carousel');
        const prevButton = carousel.querySelector('.related-articles__nav-button--prev');
        const nextButton = carousel.querySelector('.related-articles__nav-button--next');
        const paginationDots = carousel.querySelectorAll('.related-articles__pagination-dot');
        
        if (!carouselContainer || !prevButton || !nextButton) {
            return;
        }

        let currentIndex = 0;
        const cards = carouselContainer.querySelectorAll('.related-articles__card');
        const totalCards = cards.length;
        const cardsPerView = getCardsPerView();
        const maxIndex = Math.max(0, totalCards - cardsPerView);

        // Initialize carousel state
        updateCarouselState();

        // Event listeners
        prevButton.addEventListener('click', function() {
            if (currentIndex > 0) {
                currentIndex--;
                updateCarouselState();
            }
        });

        nextButton.addEventListener('click', function() {
            if (currentIndex < maxIndex) {
                currentIndex++;
                updateCarouselState();
            }
        });

        // Pagination dot click handlers
        paginationDots.forEach(function(dot, index) {
            dot.addEventListener('click', function() {
                currentIndex = index;
                updateCarouselState();
            });
        });

        // Touch/swipe support for mobile
        let startX = 0;
        let startY = 0;
        let isDragging = false;

        carouselContainer.addEventListener('touchstart', function(e) {
            startX = e.touches[0].clientX;
            startY = e.touches[0].clientY;
            isDragging = true;
        });

        carouselContainer.addEventListener('touchmove', function(e) {
            if (!isDragging) return;
            
            e.preventDefault();
        });

        carouselContainer.addEventListener('touchend', function(e) {
            if (!isDragging) return;
            
            const endX = e.changedTouches[0].clientX;
            const endY = e.changedTouches[0].clientY;
            const diffX = startX - endX;
            const diffY = startY - endY;
            
            // Only handle horizontal swipes
            if (Math.abs(diffX) > Math.abs(diffY) && Math.abs(diffX) > 50) {
                if (diffX > 0 && currentIndex < maxIndex) {
                    // Swipe left - next
                    currentIndex++;
                } else if (diffX < 0 && currentIndex > 0) {
                    // Swipe right - previous
                    currentIndex--;
                }
                updateCarouselState();
            }
            
            isDragging = false;
        });

        // Keyboard navigation
        carousel.addEventListener('keydown', function(e) {
            if (e.key === 'ArrowLeft' && currentIndex > 0) {
                currentIndex--;
                updateCarouselState();
            } else if (e.key === 'ArrowRight' && currentIndex < maxIndex) {
                currentIndex++;
                updateCarouselState();
            }
        });

        // Handle window resize
        window.addEventListener('resize', function() {
            const newCardsPerView = getCardsPerView();
            if (newCardsPerView !== cardsPerView) {
                currentIndex = Math.min(currentIndex, Math.max(0, totalCards - newCardsPerView));
                updateCarouselState();
            }
        });

        function updateCarouselState() {
            const cardsPerView = getCardsPerView();
            const maxIndex = Math.max(0, totalCards - cardsPerView);
            
            // Clamp current index
            currentIndex = Math.max(0, Math.min(currentIndex, maxIndex));
            
            // Update carousel position
            const cardWidth = cards[0] ? cards[0].offsetWidth + 24 : 352; // 328px + 24px gap
            const translateX = -currentIndex * cardWidth;
            carouselContainer.style.transform = `translateX(${translateX}px)`;
            
            // Update button states
            prevButton.disabled = currentIndex === 0;
            nextButton.disabled = currentIndex >= maxIndex;
            
            // Update pagination dots
            paginationDots.forEach(function(dot, index) {
                dot.classList.toggle('related-articles__pagination-dot--active', index === currentIndex);
            });
            
            // Update ARIA attributes
            prevButton.setAttribute('aria-label', currentIndex === 0 ? 'Previous articles (disabled)' : 'Previous articles');
            nextButton.setAttribute('aria-label', currentIndex >= maxIndex ? 'Next articles (disabled)' : 'Next articles');
        }

        function getCardsPerView() {
            const containerWidth = carousel.offsetWidth;
            
            if (containerWidth <= 480) {
                return 1; // Mobile
            } else if (containerWidth <= 768) {
                return 1; // Tablet
            } else {
                return 2; // Desktop
            }
        }
    }

    // Auto-play functionality (optional)
    function initAutoPlay(carousel, interval = 5000) {
        const nextButton = carousel.querySelector('.related-articles__nav-button--next');
        let autoPlayInterval;
        
        function startAutoPlay() {
            autoPlayInterval = setInterval(function() {
                if (nextButton && !nextButton.disabled) {
                    nextButton.click();
                } else {
                    // Reset to first slide
                    const prevButton = carousel.querySelector('.related-articles__nav-button--prev');
                    if (prevButton) {
                        // Reset to beginning
                        const carouselContainer = carousel.querySelector('.related-articles__carousel');
                        const cards = carouselContainer.querySelectorAll('.related-articles__card');
                        const totalCards = cards.length;
                        const cardsPerView = getCardsPerView();
                        const maxIndex = Math.max(0, totalCards - cardsPerView);
                        
                        if (maxIndex > 0) {
                            // Go to first slide
                            const firstDot = carousel.querySelector('.related-articles__pagination-dot');
                            if (firstDot) {
                                firstDot.click();
                            }
                        }
                    }
                }
            }, interval);
        }
        
        function stopAutoPlay() {
            if (autoPlayInterval) {
                clearInterval(autoPlayInterval);
            }
        }
        
        // Start auto-play on mouse leave
        carousel.addEventListener('mouseleave', startAutoPlay);
        
        // Stop auto-play on mouse enter
        carousel.addEventListener('mouseenter', stopAutoPlay);
        
        // Stop auto-play when user interacts
        carousel.addEventListener('click', stopAutoPlay);
        carousel.addEventListener('touchstart', stopAutoPlay);
        
        // Start auto-play initially
        startAutoPlay();
    }

    // Expose functions for external use
    window.RelatedArticlesCarousel = {
        init: initCarousel,
        initAutoPlay: initAutoPlay
    };

})();