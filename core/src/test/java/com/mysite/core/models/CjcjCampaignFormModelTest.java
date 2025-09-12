package com.mysite.core.models;

import com.adobe.cq.export.json.ComponentExporter;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import com.mysite.core.testcontext.AppAemContext;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class CjcjCampaignFormModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(CjcjCampaignFormModel.class);
        context.addModelsForPackage("com.mysite.core.models");
    }

    @Test
    void testModelWithProperties() {
        // Given
        context.create().page("/content/mypage");
        Resource resource = context.create().resource("/content/mypage/cjcjcampaignform",
            "jcr:primaryType", "nt:unstructured",
            "sling:resourceType", "mysite/components/cjcjcampaignform",
            "title", "Test Campaign",
            "description", "Test Description",
            "decorativeImage", "/content/dam/test/image.svg",
            "formAction", "/bin/test-submit",
            "submitButtonText", "Submit Now"
        );

        // When
        CjcjCampaignFormModel model = resource.adaptTo(CjcjCampaignFormModel.class);

        // Then
        assertNotNull(model);
        assertEquals("Test Campaign", model.getTitle());
        assertEquals("Test Description", model.getDescription());
        assertEquals("/content/dam/test/image.svg", model.getDecorativeImage());
        assertEquals("/bin/test-submit", model.getFormAction());
        assertEquals("Submit Now", model.getSubmitButtonText());
        assertEquals(CjcjCampaignFormModel.RESOURCE_TYPE, model.getExportedType());
    }

}
