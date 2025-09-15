// Brand Ambassadors Component JavaScript
(function() {
    'use strict';

    // Initialize component when DOM is ready
    document.addEventListener('DOMContentLoaded', function() {
        initializeBrandAmbassadors();
    });

    function initializeBrandAmbassadors() {
        const components = document.querySelectorAll('.js--brand-ambassadors');
        
        components.forEach(function(component) {
            initializePillTabs(component);
            initializeAthleteCards(component);
        });
    }

    function initializePillTabs(component) {
        const pills = component.querySelectorAll('.c-brand-ambassadors__pill');
        
        pills.forEach(function(pill) {
            pill.addEventListener('click', function() {
                // Remove active class from all pills
                pills.forEach(function(p) {
                    p.classList.remove('c-brand-ambassadors__pill--active');
                });
                
                // Add active class to clicked pill
                this.classList.add('c-brand-ambassadors__pill--active');
                
                // Get tab label for filtering (if needed)
                const tabLabel = this.getAttribute('data-tab-label');
                console.log('Selected tab:', tabLabel);
                
                // Here you could add logic to filter athletes by category
                // For now, we'll just update the visual state
            });
        });
    }

    function initializeAthleteCards(component) {
        const athleteCards = component.querySelectorAll('.c-brand-ambassadors__athlete-card');
        
        athleteCards.forEach(function(card) {
            // Add hover effects
            card.addEventListener('mouseenter', function() {
                this.style.transform = 'translateY(-4px)';
                this.style.boxShadow = '0 8px 25px rgba(0, 0, 0, 0.15)';
                this.style.transition = 'all 0.3s ease';
            });
            
            card.addEventListener('mouseleave', function() {
                this.style.transform = 'translateY(0)';
                this.style.boxShadow = 'none';
            });
        });
    }

    // Export for potential external use
    window.BrandAmbassadors = {
        init: initializeBrandAmbassadors
    };

})();
