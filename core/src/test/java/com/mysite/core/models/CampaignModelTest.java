package com.mysite.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for CampaignModel
 * Tests component data binding, business logic, and error handling
 */
@ExtendWith(AemContextExtension.class)
class CampaignModelTest {

    private final AemContext context = new AemContext();
    private CampaignModel model;

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(CampaignModel.class);
        context.load().json("/test-data/campaign.json", "/content/test");
        model = context.request().adaptTo(CampaignModel.class);
    }

    @Test
    void testModelInitialization() {
        assertThat(model).isNotNull();
    }

    @Test
    void testGetTitle() {
        assertThat(model.getTitle()).isEqualTo("Lorem Ipsum");
    }

    @Test
    void testGetDescription() {
        assertThat(model.getDescription()).isEqualTo("Bibigo embodies the spirit of Korean cuisine, bringing families together through shared meals.");
    }

    @Test
    void testGetNameLabel() {
        assertThat(model.getNameLabel()).isEqualTo("Your Name");
    }

    @Test
    void testGetEmailLabel() {
        assertThat(model.getEmailLabel()).isEqualTo("Email");
    }

    @Test
    void testGetPhoneLabel() {
        assertThat(model.getPhoneLabel()).isEqualTo("Phone");
    }

    @Test
    void testGetCompanyLabel() {
        assertThat(model.getCompanyLabel()).isEqualTo("Company / Field of work");
    }

    @Test
    void testGetMessageLabel() {
        assertThat(model.getMessageLabel()).isEqualTo("What are you looking for?");
    }

    @Test
    void testGetSubmitButtonText() {
        assertThat(model.getSubmitButtonText()).isEqualTo("keep in touch");
    }

    @Test
    void testGetBackgroundImage() {
        assertThat(model.getBackgroundImage()).isEqualTo("/content/dam/mysite/campaign-background.jpg");
    }

    @Test
    void testGetLogoImage() {
        assertThat(model.getLogoImage()).isEqualTo("/content/dam/mysite/campaign-logo.png");
    }

    @Test
    void testIsConfigured() {
        assertThat(model.isConfigured()).isTrue();
    }

    @Test
    void testIsConfiguredWithEmptyTitle() {
        context.currentResource("/content/test/empty");
        CampaignModel emptyModel = context.request().adaptTo(CampaignModel.class);
        assertThat(emptyModel.isConfigured()).isFalse();
    }
}
