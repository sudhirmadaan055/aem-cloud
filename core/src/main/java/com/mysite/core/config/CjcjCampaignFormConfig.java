package com.mysite.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
    name = "CJCJ Campaign Form Configuration",
    description = "Configuration for CJCJ Campaign Form component and Adobe Journey Optimizer integration"
)
public @interface CjcjCampaignFormConfig {

    @AttributeDefinition(
        name = "AJO Endpoint",
        description = "Adobe Journey Optimizer API endpoint URL",
        required = true
    )
    String ajoEndpoint() default "https://your-ajo-instance.adobe.io/api/v1/events";

    @AttributeDefinition(
        name = "AJO Passkey",
        description = "Adobe Journey Optimizer API passkey for authentication",
        required = true
    )
    String ajoPasskey();

    @AttributeDefinition(
        name = "AJO Timeout",
        description = "Request timeout in milliseconds for AJO API calls",
        required = false
    )
    int ajoTimeout() default 30000;

    @AttributeDefinition(
        name = "Enable AJO Integration",
        description = "Enable/disable Adobe Journey Optimizer integration",
        required = false
    )
    boolean enableAJOIntegration() default true;

    @AttributeDefinition(
        name = "Fallback Email",
        description = "Fallback email address for form submissions when AJO is disabled",
        required = false
    )
    String fallbackEmail() default "admin@mysite.com";
}
