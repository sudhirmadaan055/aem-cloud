(function() {
    'use strict';
    
    // Wait for DOM to be ready
    document.addEventListener('DOMContentLoaded', function() {
        const form = document.getElementById('cjcj-campaign-form');
        const submitBtn = document.getElementById('campaign-submit-btn');
        const submitText = submitBtn.querySelector('.campaign__submit-text');
        const submitLoading = submitBtn.querySelector('.campaign__submit-loading');
        const successMessage = document.querySelector('.campaign__message--success');
        const errorMessage = document.querySelector('.campaign__message--error');
        const successText = successMessage.querySelector('.campaign__message-text');
        const errorText = errorMessage.querySelector('.campaign__message-text');
        
        if (!form) return;
        
        // Hide messages initially
        function hideMessages() {
            successMessage.style.display = 'none';
            errorMessage.style.display = 'none';
        }
        
        // Show success message
        function showSuccess(message) {
            hideMessages();
            successText.textContent = message;
            successMessage.style.display = 'block';
            successMessage.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }
        
        // Show error message
        function showError(message) {
            hideMessages();
            errorText.textContent = message;
            errorMessage.style.display = 'block';
            errorMessage.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }
        
        // Set loading state
        function setLoading(loading) {
            if (loading) {
                submitBtn.disabled = true;
                submitText.style.display = 'none';
                submitLoading.style.display = 'inline';
            } else {
                submitBtn.disabled = false;
                submitText.style.display = 'inline';
                submitLoading.style.display = 'none';
            }
        }
        
        // Form submission handler
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            
            // Hide any previous messages
            hideMessages();
            
            // Set loading state
            setLoading(true);
            
            // Collect form data
            const formData = new FormData(form);
            
            // Send AJAX request
            fetch(form.action, {
                method: 'POST',
                body: formData,
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setLoading(false);
                
                if (data.success) {
                    showSuccess(data.message || 'Form submitted successfully!');
                    form.reset(); // Clear the form
                } else {
                    showError(data.message || 'Failed to submit form. Please try again.');
                }
            })
            .catch(error => {
                setLoading(false);
                console.error('Form submission error:', error);
                showError('An error occurred while submitting the form. Please try again.');
            });
        });
        
        // Auto-hide messages after 5 seconds
        function autoHideMessages() {
            setTimeout(() => {
                hideMessages();
            }, 5000);
        }
        
        // Add event listeners for auto-hide
        successMessage.addEventListener('click', hideMessages);
        errorMessage.addEventListener('click', hideMessages);
        
        // Auto-hide after showing
        const originalShowSuccess = showSuccess;
        const originalShowError = showError;
        
        showSuccess = function(message) {
            originalShowSuccess(message);
            autoHideMessages();
        };
        
        showError = function(message) {
            originalShowError(message);
            autoHideMessages();
        };
    });
})();