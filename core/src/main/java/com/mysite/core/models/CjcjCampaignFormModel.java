package com.mysite.core.models;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    adapters = {CjcjCampaignFormModel.class, ComponentExporter.class},
    resourceType = CjcjCampaignFormModel.RESOURCE_TYPE
)
@Exporter(
    name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
    extensions = ExporterConstants.SLING_MODEL_EXTENSION
)
public class CjcjCampaignFormModel implements ComponentExporter {

    private static final Logger logger = LoggerFactory.getLogger(CjcjCampaignFormModel.class);
    
    public static final String RESOURCE_TYPE = "mysite/components/cjcjcampaignform";

    @ValueMapValue
    @Default(values = "Lorem Ipsum")
    private String title;

    @ValueMapValue
    @Default(values = "Bibigo embodies the spirit of Korean cuisine, bringing families together through shared meals.")
    private String description;

    @ValueMapValue
    @Default(values = "/content/dam/mysite/illustrations/campaign-illustration.svg")
    private String decorativeImage;

    @ValueMapValue
    @Default(values = "/bin/cjcj-campaign-form-submit")
    private String formAction;

    @ValueMapValue
    @Default(values = "KEEP IN TOUCH")
    private String submitButtonText;

    @ValueMapValue
    @Default(values = "Submit")
    private String submitButtonLabel;

    private Map<String, String> formFields;

    @PostConstruct
    protected void init() {
        formFields = new HashMap<>();
        formFields.put("yourName", "Your Name");
        formFields.put("email", "Email");
        formFields.put("phone", "Phone");
        formFields.put("companyField", "Company / Field of work");
        formFields.put("message", "What are you looking for?");
        
        logger.info("CjcjCampaignFormModel initialized with title: {}", title);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDecorativeImage() {
        return decorativeImage;
    }

    public String getFormAction() {
        return formAction;
    }

    public String getSubmitButtonText() {
        return submitButtonText != null ? submitButtonText : submitButtonLabel;
    }

    public Map<String, String> getFormFields() {
        return formFields;
    }

    @Override
    public String getExportedType() {
        return RESOURCE_TYPE;
    }
}
