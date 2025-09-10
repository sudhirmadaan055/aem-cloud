package com.mysite.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for RelatedArticlesModel
 * Tests component data binding, business logic, and error handling
 */
@ExtendWith(AemContextExtension.class)
class RelatedArticlesModelTest {

    private final AemContext context = new AemContext();
    private RelatedArticlesModel model;

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(RelatedArticlesModel.class);
        context.load().json("/test-data/relatedarticles.json", "/content/test");
        model = context.request().adaptTo(RelatedArticlesModel.class);
    }

    @Test
    void testModelInitialization() {
        assertThat(model).isNotNull();
    }

    @Test
    void testGetSectionTitle() {
        assertThat(model.getSectionTitle()).isEqualTo("Related Articles");
    }

    @Test
    void testGetSectionDescription() {
        assertThat(model.getSectionDescription()).isEqualTo("Dive deeper into the stories, innovations, and how our products are shaping kitchens, markets, and food culture worldwide.");
    }

    @Test
    void testGetExploreMoreButtonText() {
        assertThat(model.getExploreMoreButtonText()).isEqualTo("Explore more");
    }

    @Test
    void testGetExploreMoreButtonLink() {
        assertThat(model.getExploreMoreButtonLink()).isEqualTo("/content/articles");
    }

    @Test
    void testGetArticles() {
        assertThat(model.getArticles()).isNotNull();
        assertThat(model.getArticles()).hasSize(2);
    }

    @Test
    void testIsConfigured() {
        assertThat(model.isConfigured()).isTrue();
    }

    @Test
    void testGetExportedType() {
        assertThat(model.getExportedType()).isEqualTo("mysite/components/relatedarticles");
    }

    @Test
    void testModelWithNullValues() {
        // Test with empty resource
        context.create().resource("/content/empty");
        RelatedArticlesModel emptyModel = context.request().adaptTo(RelatedArticlesModel.class);
        
        assertThat(emptyModel).isNotNull();
        assertThat(emptyModel.getSectionTitle()).isEqualTo("Related Articles"); // Default value
        assertThat(emptyModel.getSectionDescription()).isEqualTo("Dive deeper into the stories, innovations, and how our products are shaping kitchens, markets, and food culture worldwide."); // Default value
        assertThat(emptyModel.getExploreMoreButtonText()).isEqualTo("Explore more"); // Default value
        assertThat(emptyModel.getExploreMoreButtonLink()).isNull();
        assertThat(emptyModel.getArticles()).isEmpty();
        assertThat(emptyModel.isConfigured()).isTrue(); // Has default title
    }

    @Test
    void testModelWithEmptyTitle() {
        context.create().resource("/content/empty-title", "sectionTitle", "");
        RelatedArticlesModel emptyTitleModel = context.request().adaptTo(RelatedArticlesModel.class);
        
        assertThat(emptyTitleModel).isNotNull();
        assertThat(emptyTitleModel.getSectionTitle()).isEqualTo("Related Articles"); // Default value
        assertThat(emptyTitleModel.isConfigured()).isTrue(); // Has default title
    }

    @Test
    void testModelWithNullTitle() {
        context.create().resource("/content/null-title", "sectionTitle", null);
        RelatedArticlesModel nullTitleModel = context.request().adaptTo(RelatedArticlesModel.class);
        
        assertThat(nullTitleModel).isNotNull();
        assertThat(nullTitleModel.getSectionTitle()).isEqualTo("Related Articles"); // Default value
        assertThat(nullTitleModel.isConfigured()).isTrue(); // Has default title
    }
}
