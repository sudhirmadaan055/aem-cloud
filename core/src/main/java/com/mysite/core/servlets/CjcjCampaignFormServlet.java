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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component(
    service = Servlet.class,
    property = {
        "sling.servlet.paths=/bin/cjcj-campaign-form-submit",
        "sling.servlet.methods=POST",
        "sling.servlet.extensions=json"
    },
    configurationPolicy = ConfigurationPolicy.REQUIRE
)
@Designate(ocd = CjcjCampaignFormConfig.class)
public class CjcjCampaignFormServlet extends SlingAllMethodsServlet {

    private static final Logger logger = LoggerFactory.getLogger(CjcjCampaignFormServlet.class);
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private String dataCollectionEndpoint;
    private String imsTokenEndpoint;
    private String clientId;
    private String clientSecret;
    private String imsOrgId;
    private String datasetId;
    private String schemaId;
    private String sandboxName;
    private String flowId;
    private int requestTimeout;
    private boolean enableDataCollectionIntegration;
    private String fallbackEmail;
    
    // Event Register configuration
    private String eventDatasetId;
    private String eventSchemaId;
    
    @Activate
    protected void activate(CjcjCampaignFormConfig config) {
        this.dataCollectionEndpoint = config.dataCollectionEndpoint();
        this.imsTokenEndpoint = config.imsTokenEndpoint();
        this.clientId = config.clientId();
        this.clientSecret = config.clientSecret();
        this.imsOrgId = config.imsOrgId();
        this.datasetId = config.datasetId();
        this.schemaId = config.schemaId();
        this.sandboxName = config.sandboxName();
        this.flowId = config.flowId();
        this.requestTimeout = config.requestTimeout();
        this.enableDataCollectionIntegration = config.enableDataCollectionIntegration();
        this.fallbackEmail = config.fallbackEmail();
        
        // Event Register configuration - using hardcoded values for now
        this.eventDatasetId = "68c91bc8bec54715580eb267";
        this.eventSchemaId = "https://ns.adobe.com/verticurlpartnersandbox/schemas/d0f31c3607835dba31ba1baa72e08f992197034b5ae164cb";
        
        logger.info("CjcjCampaignFormServlet activated with Data Collection endpoint: {}, Integration enabled: {}", 
                   dataCollectionEndpoint, enableDataCollectionIntegration);
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
            String city = request.getParameter("city");
            String message = request.getParameter("message");
            
            logger.info("Form submission received - YourName: {}, Email: {}, Phone: {}, CompanyField: {}, City: {}, Message: {}", 
                        yourName, email, phone, companyField, city, message);
            
            // Validate required fields
            if (isEmpty(yourName) || isEmpty(email) || isEmpty(phone) || isEmpty(companyField) || isEmpty(city) || isEmpty(message)) {
                sendErrorResponse(response, "All fields are required");
                return;
            }
            
            // Create XDM payload for Adobe Experience Platform
            Map<String, Object> payload = createXDMPayload(yourName, email, phone, companyField, city, message);
            String jsonPayload = objectMapper.writeValueAsString(payload);
            
            logger.info("XDM Payload created: {}", jsonPayload);
            
            // Send to Adobe Experience Platform or fallback
            boolean success;
            if (enableDataCollectionIntegration) {
                // Step 1: Profile Creation
                logger.info("=== STEP 1: PROFILE CREATION API CALL ===");
                logger.info("Profile Creation - Sending payload to: {}", dataCollectionEndpoint);
                success = sendToDataCollection(jsonPayload);
                if (success) {
                    logger.info("✅ Profile Creation API Response: SUCCESS");
                    logger.info("Profile creation successful, proceeding with event registration");
                    
                    // Step 2: Event Register (automatic after successful profile creation)
                    Map<String, Object> eventPayload = createEventPayload(email);
                    String eventJsonPayload = objectMapper.writeValueAsString(eventPayload);
                    logger.info("Event payload created: {}", eventJsonPayload);
                    
                    logger.info("=== STEP 2: EVENT REGISTER API CALL ===");
                    logger.info("Event Register - Sending payload to: {}", dataCollectionEndpoint);
                    boolean eventSuccess = sendToDataCollection(eventJsonPayload);
                    if (eventSuccess) {
                        logger.info("✅ Event Register API Response: SUCCESS");
                        logger.info("Event registration successful");
                        sendSuccessResponse(response, "Form submitted and event registered successfully");
                    } else {
                        logger.warn("❌ Event Register API Response: FAILED");
                        logger.warn("Profile created but event registration failed");
                        sendSuccessResponse(response, "Form submitted successfully (event registration failed)");
                    }
                } else {
                    logger.error("❌ Profile Creation API Response: FAILED");
                    logger.error("Profile creation failed, skipping event registration");
                    sendErrorResponse(response, "Failed to submit form. Please try again.");
                }
            } else {
                // Fallback: Log form data and send success response
                logger.info("Data Collection integration disabled. Form data logged: {}", jsonPayload);
                logger.info("Fallback email: {}", fallbackEmail);
                sendSuccessResponse(response, "Form submitted successfully (logged)");
                success = true;
            }
            
        } catch (Exception e) {
            logger.error("Error processing form submission", e);
            sendErrorResponse(response, "An error occurred while processing your request");
        }
    }
    
    private Map<String, Object> createXDMPayload(String yourName, String email, String phone, String companyField, String city, String message) {
        Map<String, Object> payload = new HashMap<>();
        
        // Header section
        Map<String, Object> header = new HashMap<>();
        Map<String, Object> schemaRef = new HashMap<>();
        schemaRef.put("id", schemaId);
        schemaRef.put("contentType", "application/vnd.adobe.xed-full+json;version=1.0");
        header.put("schemaRef", schemaRef);
        header.put("imsOrgId", imsOrgId);
        header.put("datasetId", datasetId);
        
        Map<String, Object> source = new HashMap<>();
        source.put("name", "Streaming Connection XDM - CJCJ 2 - 09/15/2025, 5:54 PM");
        header.put("source", source);
        
        // Body section
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> xdmMeta = new HashMap<>();
        xdmMeta.put("schemaRef", schemaRef);
        body.put("xdmMeta", xdmMeta);
        
        Map<String, Object> xdmEntity = new HashMap<>();
        
        Map<String, Object> repo = new HashMap<>();
        String currentTime = Instant.now().toString();
        repo.put("createDate", currentTime);
        repo.put("modifyDate", currentTime);
        xdmEntity.put("_repo", repo);
        
        Map<String, Object> verticurlpartnersandbox = new HashMap<>();
        Map<String, Object> cjcjProfileFieldgrp2 = new HashMap<>();
        cjcjProfileFieldgrp2.put("company", companyField);
        cjcjProfileFieldgrp2.put("description", message);
        cjcjProfileFieldgrp2.put("email", email);
        cjcjProfileFieldgrp2.put("name", yourName);
        cjcjProfileFieldgrp2.put("phone", phone);
        cjcjProfileFieldgrp2.put("city", city);
        verticurlpartnersandbox.put("cjcj_profile_fieldgrp_2", cjcjProfileFieldgrp2);
        xdmEntity.put("_verticurlpartnersandbox", verticurlpartnersandbox);
        
        xdmEntity.put("repositoryCreatedBy", "AEM Forms");
        
        body.put("xdmEntity", xdmEntity);
        
        payload.put("header", header);
        payload.put("body", body);
        
        return payload;
    }
    
    private Map<String, Object> createEventPayload(String email) {
        Map<String, Object> payload = new HashMap<>();
        
        // Header section
        Map<String, Object> header = new HashMap<>();
        Map<String, Object> schemaRef = new HashMap<>();
        schemaRef.put("id", eventSchemaId);
        schemaRef.put("contentType", "application/vnd.adobe.xed-full+json;version=1.0");
        header.put("schemaRef", schemaRef);
        header.put("imsOrgId", imsOrgId);
        header.put("datasetId", eventDatasetId);
        
        Map<String, Object> source = new HashMap<>();
        source.put("name", "Streaming Event XDM - CJCJ 2- 09/16/2025, 1:44 PM");
        header.put("source", source);
        
        // Body section
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> xdmMeta = new HashMap<>();
        xdmMeta.put("schemaRef", schemaRef);
        body.put("xdmMeta", xdmMeta);
        
        Map<String, Object> xdmEntity = new HashMap<>();
        
        // Generate _id as Email+timestamp Hashed SHA256
        String timestamp = String.valueOf(System.currentTimeMillis());
        String idInput = email + timestamp;
        String hashedId = generateSHA256Hash(idInput);
        xdmEntity.put("_id", hashedId);
        
        // Event data
        Map<String, Object> verticurlpartnersandbox = new HashMap<>();
        Map<String, Object> cjcjevent = new HashMap<>();
        cjcjevent.put("email", email);
        cjcjevent.put("form_source", "CJCJ Campaign Form");
        verticurlpartnersandbox.put("cjcjevent", cjcjevent);
        xdmEntity.put("_verticurlpartnersandbox", verticurlpartnersandbox);
        
        xdmEntity.put("eventType", "aemForm.submission");
        
        // Identity Map
        Map<String, Object> identityMap = new HashMap<>();
        Map<String, Object> emailIdentity = new HashMap<>();
        emailIdentity.put("authenticatedState", "ambiguous");
        emailIdentity.put("id", email);
        emailIdentity.put("primary", true);
        
        identityMap.put("Email", java.util.Arrays.asList(emailIdentity));
        xdmEntity.put("identityMap", identityMap);
        
        xdmEntity.put("producedBy", "AEM Forms");
        xdmEntity.put("timestamp", Instant.now().toString());
        
        body.put("xdmEntity", xdmEntity);
        
        payload.put("header", header);
        payload.put("body", body);
        
        return payload;
    }
    
    private String generateSHA256Hash(String input) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            logger.error("Error generating SHA256 hash", e);
            return "fallback_" + System.currentTimeMillis();
        }
    }
    
    private boolean sendToDataCollection(String jsonPayload) {
        try {
            // First, get the access token
            String accessToken = getAccessToken();
            if (accessToken == null) {
                logger.error("Failed to obtain access token");
                return false;
            }
            
            // Send data to Adobe Experience Platform
            URL url = new URL(dataCollectionEndpoint + "?syncValidation=false");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("sandbox-name", sandboxName);
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            connection.setRequestProperty("x-adobe-flow-id", flowId);
            connection.setDoOutput(true);
            connection.setConnectTimeout(requestTimeout);
            connection.setReadTimeout(requestTimeout);
            
            // Send the JSON payload
            try (java.io.OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            int responseCode = connection.getResponseCode();
            logger.info("Data Collection API response code: {}", responseCode);
            
            // Log response for debugging
            if (responseCode >= 200 && responseCode < 300) {
                logger.info("✅ Data Collection API Response: SUCCESS (HTTP {})", responseCode);
                logger.info("Data successfully sent to Adobe Experience Platform");
                
                // Log success response body if available
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder responseBody = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseBody.append(line);
                    }
                    if (responseBody.length() > 0) {
                        logger.info("API Success Response Body: {}", responseBody.toString());
                    }
                } catch (Exception e) {
                    logger.debug("No response body available for success response");
                }
            } else {
                logger.error("❌ Data Collection API Response: FAILED (HTTP {})", responseCode);
                // Log error response
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    logger.error("API Error Response Body: {}", response.toString());
                } catch (Exception e) {
                    logger.error("Failed to read error response body: {}", e.getMessage());
                }
            }
            
            return responseCode >= 200 && responseCode < 300;
            
        } catch (Exception e) {
            logger.error("Error sending data to Adobe Experience Platform", e);
            return false;
        }
    }
    
    private String getAccessToken() {
        try {
            URL url = new URL(imsTokenEndpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
            connection.setConnectTimeout(requestTimeout);
            connection.setReadTimeout(requestTimeout);
            
            // Prepare the request body
            String requestBody = "grant_type=client_credentials" +
                    "&client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8) +
                    "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8) +
                    "&scope=" + URLEncoder.encode("openid,AdobeID,read_organizations,additional_info.projectedProductContext,session", StandardCharsets.UTF_8);
            
            // Send the request
            try (java.io.OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            int responseCode = connection.getResponseCode();
            logger.info("IMS Token API response code: {}", responseCode);
            
            if (responseCode >= 200 && responseCode < 300) {
                // Parse the response to get the access token
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    
                    // Parse JSON response to extract access_token
                    @SuppressWarnings("unchecked")
                    Map<String, Object> tokenResponse = objectMapper.readValue(response.toString(), Map.class);
                    String accessToken = (String) tokenResponse.get("access_token");
                    
                    if (accessToken != null) {
                        logger.info("Successfully obtained access token");
                        return accessToken;
                    } else {
                        logger.error("Access token not found in response: {}", response.toString());
                    }
                }
            } else {
                // Log error response
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    logger.error("IMS Token API error response: {}", response.toString());
                }
            }
            
        } catch (Exception e) {
            logger.error("Error obtaining access token", e);
        }
        
        return null;
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
