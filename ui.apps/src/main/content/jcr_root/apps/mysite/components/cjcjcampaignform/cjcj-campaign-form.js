(function() {
    'use strict';
    
    // Test if JavaScript is loading
    console.log('CJCJ Campaign Form: Script file loaded');
    alert('CJCJ Campaign Form: JavaScript is loading!');
    
    // Wait for DOM to be ready
    document.addEventListener('DOMContentLoaded', function() {
        console.log('CJCJ Campaign Form: JavaScript loaded and DOM ready');
        
        const form = document.getElementById('cjcj-campaign-form');
        const submitBtn = document.getElementById('campaign-submit-btn');
        const submitText = submitBtn ? submitBtn.querySelector('.campaign__submit-text') : null;
        const submitLoading = submitBtn ? submitBtn.querySelector('.campaign__submit-loading') : null;
        const successMessage = document.querySelector('.campaign__message--success');
        const errorMessage = document.querySelector('.campaign__message--error');
        const successText = successMessage ? successMessage.querySelector('.campaign__message-text') : null;
        const errorText = errorMessage ? errorMessage.querySelector('.campaign__message-text') : null;
        
        console.log('CJCJ Campaign Form: Form found:', !!form);
        console.log('CJCJ Campaign Form: Submit button found:', !!submitBtn);
        console.log('CJCJ Campaign Form: Success message found:', !!successMessage);
        console.log('CJCJ Campaign Form: Error message found:', !!errorMessage);
        
        if (!form) {
            console.error('CJCJ Campaign Form: Form not found!');
            return;
        }
        
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
            console.log('CJCJ Campaign Form: Form submit event triggered');
            e.preventDefault();
            console.log('CJCJ Campaign Form: Prevented default form submission');
            
            // Hide any previous messages
            hideMessages();
            
            // Set loading state
            setLoading(true);
            
            // Collect form data
            const formData = new FormData(form);
            const submitUrl = '/bin/cjcj-campaign-form-submit';
            console.log('CJCJ Campaign Form: Form data collected, submitting to:', submitUrl);
            
            // Send AJAX request
            fetch(submitUrl, {
                method: 'POST',
                body: formData,
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
            .then(response => {
                console.log('CJCJ Campaign Form: Response received, status:', response.status);
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('CJCJ Campaign Form: Response data:', data);
                setLoading(false);
                
                if (data.success) {
                    console.log('CJCJ Campaign Form: Success response received');
                    showSuccess(data.message || 'Form submitted successfully!');
                    form.reset(); // Clear the form
                } else {
                    console.log('CJCJ Campaign Form: Error response received');
                    showError(data.message || 'Failed to submit form. Please try again.');
                }
            })
            .catch(error => {
                setLoading(false);
                console.error('CJCJ Campaign Form: Form submission error:', error);
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
