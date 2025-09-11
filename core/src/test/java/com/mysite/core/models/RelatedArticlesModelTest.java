package com.mysite.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

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
        context.currentResource(context.resourceResolver().getResource("/content/test/jcr:content"));
        model = context.request().adaptTo(RelatedArticlesModel.class);
    }

    @Test
    void testModelInitialization() {
        assertNotNull(model);
    }

    @Test
    void testGetSectionTitle() {
        assertEquals("Related Articles", model.getSectionTitle());
    }

    @Test
    void testGetSectionDescription() {
        assertEquals("Dive deeper into the stories, innovations, and how our products are shaping kitchens, markets, and food culture worldwide.", model.getSectionDescription());
    }

    @Test
    void testGetExploreMoreButtonText() {
        assertEquals("Explore more", model.getExploreMoreButtonText());
    }

    @Test
    void testGetExploreMoreButtonLink() {
        // The link should be loaded from test data
        String link = model.getExploreMoreButtonLink();
        assertNotNull(link, "Explore more button link should not be null");
        assertEquals("/content/articles", link);
    }

    @Test
    void testGetArticles() {
        assertNotNull(model.getArticles());
        assertEquals(0, model.getArticles().size()); // No articles in simplified test data
    }

    @Test
    void testIsConfigured() {
        assertTrue(model.isConfigured());
    }

    @Test
    void testGetExportedType() {
        assertEquals("mysite/components/relatedarticles", model.getExportedType());
    }

    @Test
    void testModelWithNullValues() {
        // Test with empty resource
        context.create().resource("/content/empty");
        context.currentResource(context.resourceResolver().getResource("/content/empty"));
        RelatedArticlesModel emptyModel = context.request().adaptTo(RelatedArticlesModel.class);
        
        assertNotNull(emptyModel);
        assertEquals("Related Articles", emptyModel.getSectionTitle()); // Default value
        assertEquals("Dive deeper into the stories, innovations, and how our products are shaping kitchens, markets, and food culture worldwide.", emptyModel.getSectionDescription()); // Default value
        assertEquals("Explore more", emptyModel.getExploreMoreButtonText()); // Default value
        assertNull(emptyModel.getExploreMoreButtonLink()); // No link property set
        assertTrue(emptyModel.getArticles().isEmpty());
        assertTrue(emptyModel.isConfigured()); // Has default title
    }

    @Test
    void testModelWithEmptyTitle() {
        context.create().resource("/content/empty-title", "sectionTitle", "");
        RelatedArticlesModel emptyTitleModel = context.request().adaptTo(RelatedArticlesModel.class);
        
        assertNotNull(emptyTitleModel);
        assertEquals("Related Articles", emptyTitleModel.getSectionTitle()); // Default value
        assertTrue(emptyTitleModel.isConfigured()); // Has default title
    }

    @Test
    void testModelWithNullTitle() {
        context.create().resource("/content/null-title", "sectionTitle", null);
        RelatedArticlesModel nullTitleModel = context.request().adaptTo(RelatedArticlesModel.class);
        
        assertNotNull(nullTitleModel);
        assertEquals("Related Articles", nullTitleModel.getSectionTitle()); // Default value
        assertTrue(nullTitleModel.isConfigured()); // Has default title
    }
}
