package com.mysite.core.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.core.config.CjcjCampaignFormConfig;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component(
    service = Servlet.class,
    property = {
        "sling.servlet.paths=/bin/cjcj-campaign-form-submit",
        "sling.servlet.methods=POST"
    },
    configurationPolicy = ConfigurationPolicy.REQUIRE
)
@Designate(ocd = CjcjCampaignFormConfig.class)
public class CjcjCampaignFormServlet extends SlingAllMethodsServlet {

    private static final Logger logger = LoggerFactory.getLogger(CjcjCampaignFormServlet.class);
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private String ajoEndpoint;
    private String ajoPasskey;
    private int ajoTimeout;
    private boolean enableAJOIntegration;
    private String fallbackEmail;
    
    @Activate
    protected void activate(CjcjCampaignFormConfig config) {
        this.ajoEndpoint = config.ajoEndpoint();
        this.ajoPasskey = config.ajoPasskey();
        this.ajoTimeout = config.ajoTimeout();
        this.enableAJOIntegration = config.enableAJOIntegration();
        this.fallbackEmail = config.fallbackEmail();
        
        logger.info("CjcjCampaignFormServlet activated with AJO endpoint: {}, Integration enabled: {}", 
                   ajoEndpoint, enableAJOIntegration);
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Extract form data
            String yourName = request.getParameter("yourName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String companyField = request.getParameter("companyField");
            String message = request.getParameter("message");
            
            logger.info("Form submission received - YourName: {}, Email: {}, Phone: {}, CompanyField: {}, Message: {}", 
                        yourName, email, phone, companyField, message);
            
            // Validate required fields
            if (isEmpty(yourName) || isEmpty(email) || isEmpty(phone) || isEmpty(companyField) || isEmpty(message)) {
                sendErrorResponse(response, "All fields are required");
                return;
            }
            
            // Create JSON payload for Adobe Journey Optimizer
            Map<String, Object> payload = createAJOPayload(yourName, email, phone, companyField, message);
            String jsonPayload = objectMapper.writeValueAsString(payload);
            
            logger.info("AJOPayload created: {}", jsonPayload);
            
            // Send to Adobe Journey Optimizer or fallback
            boolean success;
            if (enableAJOIntegration) {
                success = sendToAJO(jsonPayload);
                if (success) {
                    sendSuccessResponse(response, "Form submitted successfully");
                } else {
                    sendErrorResponse(response, "Failed to submit form. Please try again.");
                }
            } else {
                // Fallback: Log form data and send success response
                logger.info("AJO integration disabled. Form data logged: {}", jsonPayload);
                logger.info("Fallback email: {}", fallbackEmail);
                sendSuccessResponse(response, "Form submitted successfully (logged)");
                success = true;
            }
            
        } catch (Exception e) {
            logger.error("Error processing form submission", e);
            sendErrorResponse(response, "An error occurred while processing your request");
        }
    }
    
    private Map<String, Object> createAJOPayload(String yourName, String email, String phone, String companyField, String message) {
        Map<String, Object> payload = new HashMap<>();
        
        // Event data
        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "cjcj-campaign-form-submission");
        event.put("timestamp", System.currentTimeMillis());
        
        // User data
        Map<String, Object> userData = new HashMap<>();
        userData.put("yourName", yourName);
        userData.put("email", email);
        userData.put("phone", phone);
        userData.put("companyField", companyField);
        userData.put("message", message);
        
        payload.put("event", event);
        payload.put("userData", userData);
        
        return payload;
    }
    
    private boolean sendToAJO(String jsonPayload) {
        try {
            URL url = new URL(ajoEndpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + ajoPasskey);
            connection.setDoOutput(true);
            connection.setConnectTimeout(ajoTimeout);
            connection.setReadTimeout(ajoTimeout);
            
            // Send the JSON payload
            try (java.io.OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            int responseCode = connection.getResponseCode();
            logger.info("AJO API response code: {}", responseCode);
            
            return responseCode >= 200 && responseCode < 300;
            
        } catch (Exception e) {
            logger.error("Error sending data to Adobe Journey Optimizer", e);
            return false;
        }
    }
    
    private void sendSuccessResponse(SlingHttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", message);
        
        try (PrintWriter writer = response.getWriter()) {
            writer.write(objectMapper.writeValueAsString(result));
        }
    }
    
    private void sendErrorResponse(SlingHttpServletResponse response, String message) throws IOException {
        response.setStatus(SlingHttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("message", message);
        
        try (PrintWriter writer = response.getWriter()) {
            writer.write(objectMapper.writeValueAsString(result));
        }
    }
    
    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
