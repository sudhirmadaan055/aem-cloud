package com.mysite.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
    name = "CJCJ Campaign Form Configuration",
    description = "Configuration for CJCJ Campaign Form component and Adobe Experience Platform Data Collection integration"
)
public @interface CjcjCampaignFormConfig {

    @AttributeDefinition(
        name = "Data Collection Endpoint",
        description = "Adobe Experience Platform Data Collection API endpoint URL",
        required = true
    )
    String dataCollectionEndpoint() default "https://dcs.adobedc.net/collection/759af8ffbdf2a85272d56f734e9b3aaf21ade6005ccd6254486f0fc98f85a072";

    @AttributeDefinition(
        name = "IMS Token Endpoint",
        description = "Adobe IMS token endpoint for authentication",
        required = true
    )
    String imsTokenEndpoint() default "https://ims-na1.adobelogin.com/ims/token/v2";

    @AttributeDefinition(
        name = "Client ID",
        description = "Adobe IMS Client ID for authentication",
        required = true
    )
    String clientId();

    @AttributeDefinition(
        name = "Client Secret",
        description = "Adobe IMS Client Secret for authentication",
        required = true
    )
    String clientSecret();

    @AttributeDefinition(
        name = "IMS Org ID",
        description = "Adobe IMS Organization ID",
        required = true
    )
    String imsOrgId() default "647D9BAC572C95F27F000101@AdobeOrg";

    @AttributeDefinition(
        name = "Dataset ID",
        description = "Adobe Experience Platform Dataset ID",
        required = true
    )
    String datasetId() default "68c80500f6853cffe7da76ab";

    @AttributeDefinition(
        name = "Schema ID",
        description = "XDM Schema ID for the profile data",
        required = true
    )
    String schemaId() default "https://ns.adobe.com/verticurlpartnersandbox/schemas/6151199493723c29a8c498dc47f8853cce430cf7da478383";

    @AttributeDefinition(
        name = "Sandbox Name",
        description = "Adobe Experience Platform Sandbox name",
        required = true
    )
    String sandboxName() default "prod";

    @AttributeDefinition(
        name = "Flow ID",
        description = "Adobe Experience Platform Flow ID",
        required = true
    )
    String flowId() default "2b41f60f-6b5d-4653-a94b-b59abc054855";

    @AttributeDefinition(
        name = "Request Timeout",
        description = "Request timeout in milliseconds for API calls",
        required = false
    )
    int requestTimeout() default 30000;

    @AttributeDefinition(
        name = "Enable Data Collection Integration",
        description = "Enable/disable Adobe Experience Platform Data Collection integration",
        required = false
    )
    boolean enableDataCollectionIntegration() default true;

    @AttributeDefinition(
        name = "Fallback Email",
        description = "Fallback email address for form submissions when integration is disabled",
        required = false
    )
    String fallbackEmail() default "admin@mysite.com";
}
