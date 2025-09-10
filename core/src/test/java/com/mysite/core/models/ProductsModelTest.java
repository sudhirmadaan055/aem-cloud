package com.mysite.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

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
        model = context.request().adaptTo(ProductsModel.class);
    }

    @Test
    void testModelInitialization() {
        assertThat(model).isNotNull();
    }

    @Test
    void testGetSectionLabel() {
        assertThat(model.getSectionLabel()).isEqualTo("Catalogue");
    }

    @Test
    void testGetTitle() {
        assertThat(model.getTitle()).isEqualTo("Our Products");
    }

    @Test
    void testGetDescription() {
        assertThat(model.getDescription()).isEqualTo("From comforting dumplings to vibrant street food, Bibigo celebrates how consumers around the world make Korean food their own.");
    }

    @Test
    void testGetExploreButtonText() {
        assertThat(model.getExploreButtonText()).isEqualTo("Explore Products");
    }

    @Test
    void testGetExploreButtonLink() {
        assertThat(model.getExploreButtonLink()).isEqualTo("/content/explore");
    }

    @Test
    void testGetProducts() {
        assertThat(model.getProducts()).isNotNull();
        assertThat(model.getProducts()).hasSize(2);
    }

    @Test
    void testIsConfigured() {
        assertThat(model.isConfigured()).isTrue();
    }

    @Test
    void testIsConfiguredWithEmptyTitle() {
        Resource resource = context.resourceResolver().getResource("/content/test");
        context.currentResource(resource);
        context.request().setAttribute("title", "");
        
        ProductsModel emptyModel = context.request().adaptTo(ProductsModel.class);
        assertThat(emptyModel.isConfigured()).isFalse();
    }

    @Test
    void testGetExportedType() {
        assertThat(model.getExportedType()).isEqualTo("mysite/components/products");
    }

    @Test
    void testDefaultValues() {
        context.load().json("/test-data/products-empty.json", "/content/empty");
        context.currentResource(context.resourceResolver().getResource("/content/empty"));
        
        ProductsModel defaultModel = context.request().adaptTo(ProductsModel.class);
        
        assertThat(defaultModel.getSectionLabel()).isEqualTo("Catalogue");
        assertThat(defaultModel.getTitle()).isEqualTo("Our Products");
        assertThat(defaultModel.getDescription()).isEqualTo("From comforting dumplings to vibrant street food, Bibigo celebrates how consumers around the world make Korean food their own.");
        assertThat(defaultModel.getExploreButtonText()).isEqualTo("Explore Products");
        assertThat(defaultModel.getProducts()).isEmpty();
    }

    @Test
    void testNullProductsHandling() {
        context.load().json("/test-data/products-null.json", "/content/null");
        context.currentResource(context.resourceResolver().getResource("/content/null"));
        
        ProductsModel nullModel = context.request().adaptTo(ProductsModel.class);
        
        assertThat(nullModel.getProducts()).isNotNull();
        assertThat(nullModel.getProducts()).isEmpty();
    }
}
