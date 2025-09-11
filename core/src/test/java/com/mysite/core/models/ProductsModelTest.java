package com.mysite.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ProductsModel
 * Tests component data binding, business logic, and error handling
 */
@ExtendWith(AemContextExtension.class)
class ProductsModelTest {

    private final AemContext context = new AemContext();
    private ProductsModel model;

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(ProductsModel.class);
        context.load().json("/test-data/products.json", "/content/test");
        context.currentResource(context.resourceResolver().getResource("/content/test/jcr:content"));
        model = context.request().adaptTo(ProductsModel.class);
    }

    @Test
    void testModelInitialization() {
        assertNotNull(model);
    }

    @Test
    void testGetSectionLabel() {
        assertEquals("Catalogue", model.getSectionLabel());
    }

    @Test
    void testGetTitle() {
        assertEquals("Our Products", model.getTitle());
    }

    @Test
    void testGetDescription() {
        assertEquals("From comforting dumplings to vibrant street food, Bibigo celebrates how consumers around the world make Korean food their own.", model.getDescription());
    }

    @Test
    void testGetExploreButtonText() {
        assertEquals("Explore Products", model.getExploreButtonText());
    }

    @Test
    void testGetExploreButtonLink() {
        assertEquals("/content/explore", model.getExploreButtonLink());
    }

    @Test
    void testGetProducts() {
        assertNotNull(model.getProducts());
        assertEquals(0, model.getProducts().size()); // No products in simplified test data
    }

    @Test
    void testIsConfigured() {
        assertTrue(model.isConfigured());
    }

    @Test
    void testIsConfiguredWithEmptyTitle() {
        // Create a resource with empty title property
        context.create().resource("/content/empty-title", "title", "");
        context.currentResource(context.resourceResolver().getResource("/content/empty-title"));
        
        ProductsModel emptyModel = context.request().adaptTo(ProductsModel.class);
        assertFalse(emptyModel.isConfigured());
    }

    @Test
    void testGetExportedType() {
        assertEquals("mysite/components/products", model.getExportedType());
    }

    @Test
    void testDefaultValues() {
        context.load().json("/test-data/products-empty.json", "/content/empty");
        context.currentResource(context.resourceResolver().getResource("/content/empty"));
        
        ProductsModel defaultModel = context.request().adaptTo(ProductsModel.class);
        
        assertEquals("Catalogue", defaultModel.getSectionLabel());
        assertEquals("Our Products", defaultModel.getTitle());
        assertEquals("From comforting dumplings to vibrant street food, Bibigo celebrates how consumers around the world make Korean food their own.", defaultModel.getDescription());
        assertEquals("Explore Products", defaultModel.getExploreButtonText());
        assertTrue(defaultModel.getProducts().isEmpty());
    }

    @Test
    void testNullProductsHandling() {
        context.load().json("/test-data/products-null.json", "/content/null");
        context.currentResource(context.resourceResolver().getResource("/content/null"));
        
        ProductsModel nullModel = context.request().adaptTo(ProductsModel.class);
        
        assertNotNull(nullModel.getProducts());
        assertTrue(nullModel.getProducts().isEmpty());
    }
}
